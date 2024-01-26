public class FileInvalidException extends Exception {
    public FileInvalidException(String message){
        System.out.println(message);
    }
    public static String fillFieldInMessage(String fn, String fileName){
        return "Error detected empty field \n" +
                "===========================\n"+
                "Problem detected with input file: " + fileName +
                "File is invalid: Field \"" + fn + "\" is empty processing stopped at this point other fields might be present\n" +
                "===========================";
    }
}
