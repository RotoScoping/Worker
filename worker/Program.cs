// See https://aka.ms/new-console-template for more information

using System.Data;
using System.Data.SqlClient;
using System.Data.SQLite;
using System.Text.RegularExpressions;
using worker;

class Program
{
    
    
    static void Main(string[] args)
    {
        CommandExecutor executor = new CommandExecutor();
        executor.execute(string.Join(" ", args));
    }
}