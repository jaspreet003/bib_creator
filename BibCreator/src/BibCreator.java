/*
ID: 2211239
Name: Jaspreet Kaur
*/




import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class BibCreator {
    public static void main(String[] args) throws IOException {
        String basePathToFiles = "C:\\Users\\Jaspreet\\Documents\\Advance OOP\\BibCreator\\src\\files\\";
        String outputBasePath = "C:\\Users\\Jaspreet\\Documents\\Advance OOP\\BibCreator\\src\\files\\";


        int noOfFiles = 10;

        System.out.println("Welcome to BibCreator!\n");

        boolean allFilesOk = checkFiles(noOfFiles, basePathToFiles);

        if(!allFilesOk) return;
        
        ArrayList<Article> articleList;

        for (int i = 1; i <= noOfFiles; i++) {
            BufferedReader readFile = null;
            try {
                articleList = new ArrayList<>();
                boolean isValidFile = false;
                String fileName = "Latex"+i+".bib";
                String filePath = basePathToFiles+fileName;
                readFile = new BufferedReader(new FileReader(filePath));

                isValidFile = processFile(readFile, fileName, articleList);

                if (isValidFile) {
                    writeFiles(articleList, outputBasePath, i);
                }else{
                    articleList.clear();
                }

            } catch (FileNotFoundException e) {
                System.out.println();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (FileInvalidException e) {

            } finally {
                readFile.close();
            }
        }

        int attempts = 0;
        BufferedReader reader = null;
        while (attempts<=2) {
            try {
                System.out.print("Please enter the name of one of the files that you want to review: ");
                String fileName = new BufferedReader(new InputStreamReader(System.in)).readLine();

                reader = new BufferedReader(new FileReader(outputBasePath + fileName));
                String line = null;

                System.out.println("\nHere are the contents of the successfully created json file: " + fileName);
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                break;
            } catch (FileNotFoundException e) {
                System.out.println("Could not open input file. File does not exist; possible it could not be created!");
                attempts++;
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
            if(attempts == 1){
                System.out.println("\nHowever you will be allowed another chance to display another filename");
            }
            if (attempts == 2) {
                System.out.println("sorry! i am unable to display your desired file! Program will exit!");
                break;
            }
        }

        System.out.println("Good bye hope you have enjoyed creating needed file using BibCreator");
    }

    public static boolean checkFiles(int noOfFiles, String basePathToFiles){
        boolean allFilesOk = true;
        for (int i = 1; i <= noOfFiles ; i++) {
            Scanner scanner = null;
            String fileName = "Latex"+i+".bib";
            try{
                String filePath = basePathToFiles+fileName;

                scanner = new Scanner(new File(filePath));

            }catch (FileNotFoundException e){
                allFilesOk = false;
                System.out.println("Could not open input file "+fileName+" for reading.\n");
                System.out.println("Please check if file exists! Program will terminate after closing any opened files.");

            }finally {
                if (scanner != null) {
                    scanner.close();
                }
            }
        }

        return allFilesOk;
    }
    public static void writeFiles(ArrayList<Article> articleList, String outputBasePath, int i) throws IOException {
        ArrayList<String> ieeeStrings = new ArrayList<String>();
        ArrayList<String> acmStrings = new ArrayList<String>();
        ArrayList<String> njStrings = new ArrayList<String>();
        for (Article article:
                articleList) {
            ieeeStrings.add(Article.createIeeeFormat(article));
            acmStrings.add(Article.createAcmFormat(article));
            njStrings.add(Article.createNjFormat(article));
        }

        PrintWriter writeIeeeFile = new PrintWriter(new FileWriter(outputBasePath+"IEEE"+i+".json"));
        try{
            for (String ieeeString: ieeeStrings
            ) {
                writeIeeeFile.println(ieeeString);
                writeIeeeFile.println();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            writeIeeeFile.close();
        }

        PrintWriter writeAcmFile = new PrintWriter(new FileWriter(outputBasePath+"ACM"+i+".json"));
        try{
            for (String acmString: acmStrings
            ) {
                writeAcmFile.println(acmString);
                writeAcmFile.println();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            writeAcmFile.close();
        }

        PrintWriter writeNJFile = new PrintWriter(new FileWriter(outputBasePath+"NJ"+i+".json"));
        try{
            for (String njString: njStrings
            ) {
                writeNJFile.println(njString);
                writeNJFile.println();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            writeNJFile.close();
        }
    }

    public static boolean processFile(BufferedReader readFile, String fileName, ArrayList<Article> articleList) throws FileInvalidException, IOException {
        String line ;
        Article currentArticle = null;
        boolean isValidFile = false;
        while ((line = readFile.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
            } else if (line.startsWith("@ARTICLE")) {
                currentArticle = new Article();
            } else if (line.startsWith("author=")) {
                currentArticle.setAuthor(line.substring((line.indexOf("{")) + 1, line.indexOf("}")));
                isValidFile = checkField(currentArticle, fileName, "author");

            } else if (line.startsWith("journal=")) {

                currentArticle.setJournal(line.substring(line.indexOf("{") + 1, line.indexOf("}")));
                isValidFile = checkField(currentArticle, fileName, "journal");

            } else if (line.startsWith("title=")) {

                currentArticle.setTitle(line.substring(line.indexOf("{") + 1, line.indexOf("}")));
                isValidFile = checkField(currentArticle, fileName, "title");

            } else if (line.startsWith("year=")) {

                currentArticle.setYear(line.substring(line.indexOf("{") + 1, line.indexOf("}")));
                isValidFile = checkField(currentArticle, fileName, "year");

            } else if (line.startsWith("volume=")) {

                currentArticle.setVolume(line.substring(line.indexOf("{") + 1, line.indexOf("}")));
                isValidFile = checkField(currentArticle, fileName, "volume");

            } else if (line.startsWith("number=")) {

                currentArticle.setNumber(line.substring(line.indexOf("{") + 1, line.indexOf("}")));
                isValidFile = checkField(currentArticle, fileName, "number");

            } else if (line.startsWith("pages=")) {

                currentArticle.setPages(line.substring(line.indexOf("{") + 1, line.indexOf("}")));
                isValidFile = checkField(currentArticle, fileName, "pages");

            } else if (line.startsWith("keywords=")) {

                currentArticle.setKeywords(line.substring(line.indexOf("{") + 1, line.indexOf("}")));
                isValidFile = checkField(currentArticle, fileName, "keywords");

            } else if (line.startsWith("doi=")) {

                currentArticle.setDoi(line.substring(line.indexOf("{") + 1, line.indexOf("}")));
                isValidFile = checkField(currentArticle, fileName, "doi");

            } else if (line.startsWith("ISSN=")) {

                currentArticle.setIssn(line.substring(line.indexOf("{") + 1, line.indexOf("}")));
                isValidFile = checkField(currentArticle, fileName, "ISSN");

            } else if (line.startsWith("month=")) {

                currentArticle.setMonth(line.substring(line.indexOf("{") + 1, line.indexOf("}")));
                isValidFile = checkField(currentArticle, fileName, "month");

            } else if (line.equals("}")) {
                articleList.add(currentArticle);
            }

        }
        return isValidFile;
    }
    public static boolean checkField(Article article, String fileName, String fieldName) throws FileInvalidException {
        switch (fieldName) {
            case "id":
                if (article.getId() == 0) {
                    throw new FileInvalidException(FileInvalidException.fillFieldInMessage(fieldName, fileName));
                }
                break;
            case "author":
                if (article.getAuthor().isEmpty()) {
                    throw new FileInvalidException(FileInvalidException.fillFieldInMessage(fieldName, fileName));
                }
                break;
            case "journal":
                if (article.getJournal().isBlank()) {
                    throw new FileInvalidException(FileInvalidException.fillFieldInMessage(fieldName, fileName));
                }
                break;
            case "title":
                if (article.getTitle().isBlank()) {
                    throw new FileInvalidException(FileInvalidException.fillFieldInMessage(fieldName, fileName));
                }
                break;
            case "year":
                if (article.getYear().isBlank()) {
                    throw new FileInvalidException(FileInvalidException.fillFieldInMessage(fieldName, fileName));
                }
                break;
            case "volume":
                if (article.getVolume().isBlank()) {
                    throw new FileInvalidException(FileInvalidException.fillFieldInMessage(fieldName, fileName));
                }
                break;
            case "number":
                if (article.getNumber().isBlank()) {
                    throw new FileInvalidException(FileInvalidException.fillFieldInMessage(fieldName, fileName));
                }
                break;
            case "pages":
                if (article.getPages().isBlank()) {
                    throw new FileInvalidException(FileInvalidException.fillFieldInMessage(fieldName, fileName));
                }
                break;
            case "keywords":
                if (article.getKeywords().isBlank()) {
                    throw new FileInvalidException(FileInvalidException.fillFieldInMessage(fieldName, fileName));
                }
                break;
            case "ISSN":
                if (article.getIssn().isBlank()) {
                    throw new FileInvalidException(FileInvalidException.fillFieldInMessage(fieldName, fileName));
                }
                break;
            case "month":
                if (article.getMonth().isBlank()) {
                    throw new FileInvalidException(FileInvalidException.fillFieldInMessage(fieldName, fileName));
                }
                break;
            case "doi":
                if (article.getDoi().isBlank()) {
                    throw new FileInvalidException(FileInvalidException.fillFieldInMessage(fieldName, fileName));
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
        return true;
    }

}




