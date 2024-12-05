package mariapiabaldoin.capstone_project_b.tools;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import mariapiabaldoin.capstone_project_b.entities.CentroEstetico;
import mariapiabaldoin.capstone_project_b.entities.Cliente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class MailgunSender {
    private String apiKey;
    private String domain;

    public MailgunSender(@Value("${mailgun.apikey}") String apiKey,
                         @Value("${mailgun.domain}") String domain) {
        this.apiKey = apiKey;
        this.domain = domain;
    }

    public void sendRegistrationEmail(Cliente recipient) {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.domain + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", "piabaldoin@gmail.com")
                .queryString("to", recipient.getEmail())
                .queryString("subject", "Registration completed!")
                .queryString("text", "Welcome " + recipient.getNome() + " to our platform!")
                .asJson();
        System.out.println(response.getBody());
    }


    public void sendRegistrationEmailBc(CentroEstetico recipient) {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.domain + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", "piabaldoin@gmail.com")
                .queryString("to", recipient.getEmail())
                .queryString("subject", "Registration completed!")
                .queryString("text", "Welcome " + recipient.getNomeCentroEstetico() + " to our platform! Please wait to be authorized before you can log in.")
                .asJson();
        System.out.println(response.getBody());
    }
}