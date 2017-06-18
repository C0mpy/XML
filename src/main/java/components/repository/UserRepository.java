package components.repository;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.JAXBHandle;
import model.Korisnik;
import org.springframework.stereotype.Component;
import util.Properties;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;


@SuppressWarnings("ALL")
@Component
public class UserRepository {

    private static DatabaseClient client;

    public void register(Korisnik korisnik) {

        // inicijalizacija database klijenta
        client = DatabaseClientFactory.newClient(util.Properties.host, Properties.port,
                util.Properties.database, util.Properties.user, util.Properties.password,
                util.Properties.auth);

        // inicijalizacija xmldokument menadzera
        XMLDocumentManager manager = client.newXMLDocumentManager();
        try {
            JAXBContext context = JAXBContext.newInstance(Korisnik.class);
            JAXBHandle<Korisnik> handle = new JAXBHandle<>(context);

            handle.set(korisnik);

            manager.write("/korisnik/" + korisnik.getId(), handle);
            client.release();
        } catch (JAXBException e) {
            e.printStackTrace();
            client.release();
        }
    }

    public Korisnik findOneByUsernameAndPassword(String username, String password) throws JAXBException {

        client = DatabaseClientFactory.newClient(util.Properties.host, Properties.port,
                util.Properties.database, util.Properties.user, util.Properties.password,
                util.Properties.auth);

        // saljemo zahtev sa queryjem za usera sa odredjenim username i pass i citamo rez kao string
        try {
            String s = client.newServerEval()
                    .xquery("declare namespace k = \"www.tim7.org/korisnik\";\n" +
                            "/k:korisnik[./k:username=\"" + username + "\" and ./k:password=\"" + password + "\"]")
                    .evalAs(String.class);

            // unmarshal xml string u Korisnik objekat
            ByteArrayInputStream bais = new ByteArrayInputStream(s.getBytes());
            Korisnik user = JAXB.unmarshal(bais, Korisnik.class);
            return user;
        }
        catch (Exception e) {
            return null;
        }

    }
}
