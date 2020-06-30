package org.sunbird.kp.test.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.sunbird.kp.test.common.AppConfig;
import org.sunbird.kp.test.common.Response;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;
import java.util.Set;

public class TestSetupUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

    /**
     * Method creates, populates and modifies the assertions for validate.json.
     * TODO: MUST be extended for response.json
     * @param dirIdMap (Mapping of directoryNames and ContentIds)
     * @param validationFileName (Should be validate.json or response.json)
     */
    public static void createDirectoriesForTestCases(Map<String, String> dirIdMap, String validationFileName, String templateDir)  {
        Set<String> directoryNames = dirIdMap.keySet();
        String mainDir = System.getProperty("user.dir") + "/src/test/resources/" + templateDir;
        directoryNames.forEach(dir -> {
            File file = new File(mainDir +"/"+ dir);
            file.mkdir();
            if (file.isDirectory()) {
//                File responseFile = new File(file.getPath() + "/" + "response.json");
//                File requestFile = new File(file.getPath() + "/" + "request.json");
//                File validateFile = new File(file.getPath() + "/" + "validate.json");
                try {
//                    if (!responseFile.exists())
//                        responseFile.createNewFile();
//                    if (!requestFile.exists())
//                        requestFile.createNewFile();
//                    if (!validateFile.exists())
//                        validateFile.createNewFile();
                    populateDataIntoValidateFiles(mainDir, dir, validationFileName, dirIdMap.get(dir));
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("No files are created");
                }
            }
        });
    }

    private static void populateDataIntoValidateFiles(String mainDir, String directoryPath, String fileName, String contentId ) {
        File file = new File(mainDir + "/" + directoryPath);
        if (file.isDirectory()) {
            try {
                FileWriter fileWriter = new FileWriter(file.getPath() + "/" + fileName);
//                HttpResponse<String> jsonNode = Unirest.get(AppConfig.config.getString("kp_base_uri") + "/content/v3/read/" + contentId + "").asString();

//               HttpResponse<String> jsonNode = Unirest.get(AppConfig.config.getString("kp_base_uri") + "/content/v3/hierarchy/" + contentId).asString();

                HttpResponse<String> jsonNode = Unirest.get(AppConfig.config.getString("kp_base_uri") + "/content/v3/hierarchy/" + contentId + "?mode=edit").asString();

                //kp_content_service_base_uri
                Response response = objectMapper.readValue(jsonNode.getBody(), Response.class);
                fileWriter.write(formGeneralAssertions(response));
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String formGeneralAssertions(Response response) {
        String oldString = "";
        try {
            oldString = objectWriter.writeValueAsString(response);
            String updatedString = oldString
                    .replaceAll("\\d{4}-\\d{2}-\\d{2}T[a-zA-Z0-9:.+]+", "@matchesDatePattern('yyyy-MM-dd')@" )
                    .replaceAll("\"resmsgid\"\\W?:\\W?\"[0-9a-zA-Z-]+\"", "\"resmsgid\" : \"@ignore@\"")
                    .replaceAll("\"previewUrl\"\\W?:\\W\"[a-zA-Z://._0-9-]+\"","\"previewUrl\" : \"@ignore@\"")
                    .replaceAll("\"downloadUrl\"\\W?:\\W\"[a-zA-Z://._0-9-]+\"","\"downloadUrl\" : \"@ignore@\"")
                    .replaceAll("\"ecarUrl\"\\W?:\\W\"[a-zA-Z://._0-9-]+\"","\"ecarUrl\" : \"@ignore@\"")
                    .replaceAll("\"streamingUrl\"\\W?:\\W\"[a-zA-Z://._0-9-]+\"","\"streamingUrl\" :\"@ignore@\"")
                    .replaceAll("\"artifactUrl\"\\W?:\\W\"[a-zA-Z://._0-9-]+\"","\"artifactUrl\" : \"@ignore@\"")
                    .replaceAll("\"s3Key\"\\W?:\\W\"[a-zA-Z:/._0-9-]+\"","\"s3Key\" : \"@ignore@\"")
                    .replaceAll("\"appId\"\\W?:\\W?\"[a-zA-Z.-]+\"","\"appId\" : \"@ignore@\"")
                    .replaceAll("\"consumerId\"\\W?:\\W?\"[a-zA-Z0-9-]+\"","\"consumerId\" : \"@ignore@\"")
                    .replaceAll("\"versionKey\"\\W?:\\W\"\\d+\"","\"versionKey\" : \"@ignore@\"")
                    .replaceAll("\"pkgVersion\"\\W?:\\W\\d+\\.\\d+","\"pkgVersion\" : \"@isNumber()@\"")
                    .replaceAll("\"parent\"\\W?:\\W\"[a-zA-Z://._0-9-]+\"","\"parent\" : \"@ignore@\"")
                    .replaceAll("\"size\"\\W?:\\W\\d+\\.\\d+","\"size\" : \"@isNumber()@\"")
                    .replaceAll("\"totalCompressedSize\"\\W?:\\W\\d+\\.\\d+","\"totalCompressedSize\" : \"@isNumber()@\"")
                    .replaceAll("\"childNodes\"\\W?:\\W\\[[^\\[]*\\]+","\"childNodes\" : \"@ignore@\"")
                    .replaceAll("\"toc_url\"\\W?:\\W\"[a-zA-Z://._0-9-]+\"","\"toc_url\" : \"@ignore@\"")
                    .replaceAll("\"leafNodes\"\\W?:\\W\\[[^\\[]*\\]+","\"leafNodes\" : \"@ignore@\"")
                    .replaceAll("\"version\"\\W?:\\W\\d+","\"version\" : \"@isNumber()@\"")
                    .replaceAll("\"compatibilityLevel\"\\W?:\\W\\d+","\"compatibilityLevel\" : \"@isNumber()@\"")
                    .replaceAll("\"leafNodesCount\"\\W?:\\W\\d+","\"leafNodesCount\" : \"@isNumber()@\"")
                    .replaceAll("\"mimeTypesCount\"\\W?:\\W\\{([^}]+)\\}+","\"mimeTypesCount\" : \"@ignore@\"")
                    .replaceAll("\"contentTypesCount\"\\W?:\\W\\{([^}]+)\\}+","\"contentTypesCount\" : \"@ignore@\"")
                    .replaceAll("\"errmsg\"\\W?:\\W\\[a-zA-Z0-9-]+","\"errmsg\" : \"@ignore@\"");
                    .replaceAll("kp_ft_\\d+","\\${identifier}")
                    .replaceAll("KP_FT_\\d+","\\${identifier}")
                    .replaceAll("kp_ft_license_\\d+", "\\${identifier}")
                    .replaceAll("\\s\\d+\\.\\d+","\"@isNumber()@\"")
                    .replaceAll("\\s\\d", "\"@isNumber()@\"")
                    .replaceAll("\"versionKey\"\\W?:\\W\"\\d+\"","\"versionKey\" : \"@isNumber()@\"")
                    .replaceAll("\"cloudStorageKey\"\\W?:\\W\"[a-zA-Z://._0-9-]+\"","\"cloudStorageKey\" : \"@ignore@\"");
            return updatedString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oldString;
    }

}
