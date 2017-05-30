package components.repository;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.JAXBHandle;
import model.Korisnik;
import org.springframework.stereotype.Component;
import util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;


@Component
public class UserRepository {

    private static DatabaseClient client;

    public void register(Korisnik korisnik) {

        client = DatabaseClientFactory.newClient(util.Properties.host, Properties.port,
                util.Properties.database, util.Properties.user, util.Properties.password,
                util.Properties.auth);

        XMLDocumentManager manager = client.newXMLDocumentManager();
        try {
            JAXBContext context = JAXBContext.newInstance(Korisnik.class);
            JAXBHandle<Korisnik> handle = new JAXBHandle<>(context);

            handle.set(korisnik);

            manager.write("/users" + korisnik.getId(), handle);
            client.release();
        }
        catch (JAXBException e) {
            e.printStackTrace();
            client.release();
        }
    }
}
