package fi.softala.meduusatunnit.utility;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class Slack {
	
	public void lahetaViesti(String viesti) {
		
		// Slack konfiguraatio
		String webhook = "https://hooks.slack.com/services/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";
		String kanava = "#general";
		String username = "meduusabot";
		String kuvake = ":meduusa:";
		
		// HTTP-requestissa lähetettävä payload
		String parametrit = "payload={\"channel\": \"" + kanava + "\", \"username\": \"" + username + "\", \"text\": \"" + viesti + "\", \"icon_emoji\": \"" + kuvake + "\"}";
		byte[] postParametrit = parametrit.getBytes(StandardCharsets.UTF_8);
		
		try {
		URL url = new URL(webhook);
		HttpsURLConnection yhteys = (HttpsURLConnection) url.openConnection();
		yhteys.setDoOutput(true);
		yhteys.setInstanceFollowRedirects(false);
		yhteys.setRequestMethod("POST");
		yhteys.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		yhteys.setRequestProperty("charset", "utf-8");
		yhteys.setRequestProperty("Content-Length", Integer.toString(postParametrit.length));
		yhteys.setUseCaches(false);
		
		DataOutputStream outputstream = new DataOutputStream(yhteys.getOutputStream());
		outputstream.write(postParametrit);
		int vastauskoodi = yhteys.getResponseCode();
		System.out.println(vastauskoodi);
		BufferedReader vastausreader = new BufferedReader(new InputStreamReader(yhteys.getInputStream()));
		String vastaus;
		while ((vastaus = vastausreader.readLine()) != null) {
			System.out.println(vastaus);
		}
		vastausreader.close();
		} catch (Exception ex) {
			System.out.println("Virhe Slack-viestiä lähettäessä: " + ex);
		}
	}

}