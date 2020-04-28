public class Utils
{
    public static int parseOption(String option)
    {
        int parsedOption = -1;
        String[] options = option.split(" ");
        if(option.length()>0)
        {
            option = options[0];
            try
            {
                parsedOption = Integer.parseInt(option);
            }
            catch (Exception e)
            {
                if(e instanceof NumberFormatException)
                {
                    System.out.println("Please enter integer for option");
                }
                else
                {
                    e.printStackTrace();
                }
            }
        }
        return parsedOption;
    }
}
