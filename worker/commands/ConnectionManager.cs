using System.Data.SQLite;

namespace worker;

public class ConnectionManager
{

    static SQLiteConnection connection;

    static public SQLiteConnection Connect()
    {
        var connection = new SQLiteConnection("Data Source=comm_output.db"); 
        try
        {
            connection.Open();
        }
        catch (SQLiteException ex)
        {
            Console.WriteLine($"Ошибка доступа к базе данных. Исключение: {ex.Message}");
        }
        return connection;
    }
    

}