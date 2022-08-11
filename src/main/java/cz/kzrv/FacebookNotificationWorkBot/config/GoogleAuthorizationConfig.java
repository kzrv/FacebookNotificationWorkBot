package cz.kzrv.FacebookNotificationWorkBot.config;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.Sheet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Configuration
public class GoogleAuthorizationConfig {
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    @Value("${application.name}")
    private String APPLICATION_NAME;
    @Value("${credentials.file.path}")
    private String credentialsFilePath;
    @Value("${tokens.directory.path}")
    private String tokensDirectoryPath;
    @Value("${api_key}")
    private String API_KEY;

    public GoogleAuthorizationConfig() {
    }
    private Credential authorize() throws IOException, GeneralSecurityException {
        InputStream in = GoogleAuthorizationConfig.class.getResourceAsStream(credentialsFilePath);
        if(in==null){
            throw new FileNotFoundException("Resource not found: " + credentialsFilePath);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        List<String> scopes = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(tokensDirectoryPath)))//new java.io.File(tokensDirectoryPath)
                .setAccessType("offline")
                .build();

        LocalServerReceiver localServerReceiver = new LocalServerReceiver.Builder().setPort(8888).build();

        return new AuthorizationCodeInstalledApp(
                flow, localServerReceiver)
                .authorize("user");
    }
    public Sheets getSheetService() throws IOException, GeneralSecurityException {
        Credential credential = authorize();
//        HttpRequestInitializer httpRequestInitializer = request -> {
//            request.setInterceptor(intercepted -> intercepted.getUrl().set("key", API_KEY));
//        };
        return new Sheets.Builder(credential.getTransport(), credential.getJsonFactory(),credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
