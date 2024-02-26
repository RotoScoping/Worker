using System.Runtime.InteropServices.JavaScript;
using System.Text.Json;
using System.Threading.Channels;

namespace worker;

public static class WorkerBuilder
{

    public static string Get(int id)
    {
        Console.Write("Введите имя воркера: ");
        var name = Console.ReadLine();
        Console.Write("Введите его зарплату: ");
        
        var sal = Console.ReadLine();
        float salary = 0;
        while (!float.TryParse(sal, out salary))
        {
            Console.Write("Ошибка ввода, введите число: ");
            sal = Console.ReadLine();
        }
        Console.WriteLine("Введите его координаты (long x, double y):");
        Console.Write("x: ");
        var x = Console.ReadLine();
        long x_long = 0; 
        while (!long.TryParse(x, out x_long))
        {
            Console.Write("Ошибка ввода, введите целое число: ");
            x = Console.ReadLine();
        }

        Console.Write("y: ");
        var y = Console.ReadLine();
        double y_doub = 0; 
        while (!double.TryParse(y, out y_doub))
        {
            Console.Write("Ошибка ввода, введите число: ");
            y = Console.ReadLine();
        }
        Console.Write("Введите когда воркер начал работу (формат dd.MM.yyyy: ");
        var startDate  = Console.ReadLine();
        string format = "dd.MM.yyyy";
        DateTime date;
        while (!DateTime.TryParseExact(startDate, format, System.Globalization.CultureInfo.InvariantCulture,
                   System.Globalization.DateTimeStyles.None, out date))
        {
            Console.Write("Неверный формат даты! Попробуйте еще раз: ");
            startDate  = Console.ReadLine();
        }
        
        Console.Write("Введите название компании: ");
        var companyName  = Console.ReadLine();
        Console.WriteLine("Введите тип компании (COMMERCIAL,PUBLIC,GOVERNMENT): ");
        var companyType  = Console.ReadLine();
        while (!(companyType.ToUpper() == "COMMERCIAL" || companyType.ToUpper() == "PUBLIC" ||
               companyType.ToUpper() == "GOVERNMENT"))
        {
            Console.Write("Такого типа не существует! Попробуйте еще раз: ");
            companyType  = Console.ReadLine();
        }
        
        Console.Write("Введите адрес компании: ");
        Console.Write("Почтовый индекс: ");
        var index  = Console.ReadLine();
        Console.Write("Название города: ");
        var townName  = Console.ReadLine();
        Console.WriteLine("Введите его координаты (double x, int y, double z):");
        Console.Write("x: ");
        var xTown = Console.ReadLine();
        double x_town_doub = 0; 
        while (!double.TryParse(xTown, out x_town_doub))
        {
            Console.Write("Ошибка ввода, введите число: ");
            xTown = Console.ReadLine();
        }
        Console.Write("y: ");
        var yTown = Console.ReadLine();
        int y_town_int = 0; 
        while (!int.TryParse(yTown, out y_town_int))
        {
            Console.Write("Ошибка ввода, введите целое число: ");
            yTown = Console.ReadLine();
        }
        Console.Write("z: ");
        var zTown = Console.ReadLine();
        double z_town_doub = 0; 
        while (!double.TryParse(zTown, out z_town_doub))
        {
            Console.Write("Ошибка ввода, введите число: ");
            zTown = Console.ReadLine();
        }
        Console.Write("Введите статус воркера (FIRED,HIRED,RECOMMENDED_FOR_PROMOTION,REGULAR,PROBATION): ");
        var status  = Console.ReadLine();
        while (!(status.ToUpper() == "FIRED" || status.ToUpper() == "HIRED" ||
                 status.ToUpper() == "RECOMMENDED_FOR_PROMOTION" || status.ToUpper() == "REGULAR" || status.ToUpper() == "PROBATION"))
        {
            Console.Write("Такого статуса не существует! Попробуйте еще раз: ");
            status  = Console.ReadLine();
        }
        Console.Write("Введите должность воркера (HUMAN_RESOURCES,DEVELOPER,LEAD_DEVELOPER,CLEANER,MANAGER_OF_CLEANING): ");
        var position  = Console.ReadLine();
        while (!(position.ToUpper() == "HUMAN_RESOURCES" || position.ToUpper() == "DEVELOPER" ||
                 position.ToUpper() == "LEAD_DEVELOPER" || position.ToUpper() == "CLEANER" || position.ToUpper() == "MANAGER_OF_CLEANING"))
        {
            Console.Write("Такой должности не существует! Попробуйте еще раз: ");
            position  = Console.ReadLine();
        }

        var worker = new
        {
            Id = id, 
            Name = name,
            Salary = salary,
            StartDate = date, 
            Position = position.ToUpper(), 
            Status = status.ToUpper(), 
            Coordinates = new Coordinates()
            {
                X = x_long, Y = y_doub
            },
            Organization = new Organization()
            {
                FullName = companyName, 
                Type = companyType.ToUpper(), 
                PostalAddress = new Address()
                {
                    ZipCode = index, 
                    Town = new Location()
                    {
                        Name = townName, 
                        X = x_town_doub, 
                        Y = y_town_int, 
                        Z = z_town_doub
                    }
                }
            }
        };
        string json = JsonSerializer.Serialize(worker);
        Console.WriteLine(json);
        return json; 
    }

    public static int GetId()
    {
        int int_id; 
        Console.Write("Введите id нужного воркера: ");
        var str_id= Console.ReadLine(); 
        while (!int.TryParse(str_id, out int_id))
        {
            Console.Write("Ошибка ввода, введите целое число: ");
            str_id = Console.ReadLine();
        }

        return int_id; 

    }
    
}