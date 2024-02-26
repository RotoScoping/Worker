using System.Data;
using System.Data.SQLite;
using System.Text;
using System.Text.RegularExpressions;

namespace worker;

public class CommandExecutor
{
    private Dictionary<string, Func<string, string>> commands;

    
    public static string CurrentPathScript { get; set;  }
    

     public CommandExecutor()
    {
        commands = new Dictionary<string, Func<string, string>>();
        Init(commands);
    }


    public void execute(string command)
    {
        command = command.Trim(); 
        var pair = command.Split(" ");
        if (pair.Length == 2)
        {
            if (pair[0] == "execute_script")
            {
                Console.WriteLine(
                    commands.GetValueOrDefault(pair[0], nonValid =>
                    {
                        Console.WriteLine($"ОШИБКА: Неизвестная команда '{nonValid}' для 'worker'\n");
                        return commands["help"].Invoke("available_commands");
                    }).Invoke(pair[1]));
                return;
            }
        }

        if (String.IsNullOrEmpty(command))
        {
            Console.WriteLine(
                commands["help"].Invoke("empty_command"));
            return;
        }

        Console.WriteLine(
            commands.GetValueOrDefault(command, nonValid =>
            {
                Console.WriteLine($"ОШИБКА: Неизвестная команда '{nonValid}' для 'worker'\n");
                return commands["help"].Invoke("available_commands");
            }).Invoke(command));
    }

    

        public void Init(Dictionary<string, Func<string, string>> commands)
        {
            Func<string, string> getFunc = command =>
            {
                using (HttpClient client = new HttpClient())
                {
                    HttpResponseMessage response =
                        client.SendAsync(new HttpRequestMessage(HttpMethod.Get,
                            $"http://localhost:9000/worker/{command}")).Result;

                    if (response.IsSuccessStatusCode)
                    {
                        return Regex.Unescape(response.Content.ReadAsStringAsync().Result);
                    }

                    return $"HTTP request failed with status code: {response.StatusCode}";
                }
            };

            Func<string, string> scriptFunc = path =>
            {
                var fullPath = Path.GetFullPath(path);
                
                if (string.IsNullOrEmpty(fullPath))
                {
                    return "Скрипт не найден!";
                }

                if (fullPath != CurrentPathScript)
                {
                    string[] lines; 
                    try
                    {
                        lines = File.ReadAllLines(path);
                    }
                    catch (FileNotFoundException e)
                    {
                        return "Такого скрипта нет, введите абсолютный путь:";

                    }
                    CurrentPathScript = fullPath;

                    foreach (var command in lines)
                    {
                        execute(command);
                    }


                    return $"{path} Выполнен!";
                } 
                return "Циклический вызов скрипта";

            };

            Func<string, string> helpFunc = command =>
            {
                var sqLiteCommand = ConnectionManager.Connect().CreateCommand();
                sqLiteCommand.CommandText = $"SELECT out FROM outputs WHERE comm='{command}'";
                DataTable data = new DataTable();
                SQLiteDataAdapter adapter = new SQLiteDataAdapter(sqLiteCommand);
                adapter.Fill(data);
                var dataRow = data.Rows[0];
                return Regex.Unescape(dataRow.Field<string>("out"));
            };

            Func<string, string> postFunc = command =>
            {
                using (var httpClient = new HttpClient())
                {
                    int id = 0;
                    if (command == "update_id")
                    {
                        id = WorkerBuilder.GetId();
                    }

                    var json = WorkerBuilder.Get(id);
                    var content = new StringContent(json, Encoding.UTF8, "application/json");
                    var response = httpClient.PostAsync($"http://localhost:9000/worker/{command}", content).Result;

                    // Обрабатываем ответ
                    if (response.IsSuccessStatusCode)
                    {
                        Console.WriteLine("Данные успешно отправлены.");
                        return Regex.Unescape(response.Content.ReadAsStringAsync().Result);
                    }

                    return $"Ошибка: {response.StatusCode}";
                }
            };
            Func<string, string> getFuncWithParam = command =>
            {
                using (var httpClient = new HttpClient())
                {
                    try
                    {
                        var id = WorkerBuilder.GetId();
                        string url = $"http://localhost:9000/worker/{command}?id={id}";
                        HttpResponseMessage response = httpClient.GetAsync(url).Result;
                        if (response.IsSuccessStatusCode)
                        {
                            return Regex.Unescape(response.Content.ReadAsStringAsync().Result);
                        }

                        return $"Ошибка: {response.StatusCode}";
                    }
                    catch (Exception e)
                    {
                        return $"Ошибка: {e.Message}";
                    }
                }
            };

            commands.Add("help", helpFunc);
            commands.Add("show", getFunc);
            commands.Add("info", getFunc);
            commands.Add("save", getFunc);
            commands.Add("exit", getFunc);
            commands.Add("clear", getFunc);
            commands.Add("remove_first", getFunc);
            commands.Add("execute_script", scriptFunc);
            commands.Add("average_of_salary", getFunc);
            commands.Add("print_field_ascending_status", getFunc);
            commands.Add("print_field_descending_organization", getFunc);
            commands.Add("add", postFunc);  
            commands.Add("remove_by_id", getFuncWithParam); 
            commands.Add("update_id", postFunc);  
            commands.Add("add_if_max", postFunc);  
            commands.Add("add_if_min", postFunc); 
        }
    
}