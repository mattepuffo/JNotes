package com.mp.jnotes;

import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.List;

public class XmlDb {

    public void create(String inputFile, List<Nota> list) throws ParserConfigurationException, TransformerException {
        String ext = FilenameUtils.getExtension(inputFile);
        if (ext.isEmpty()) {
            inputFile += ".xml";
        }

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        Element elRoot = doc.createElement("note");
        doc.appendChild(elRoot);

        for (Nota n : list) {
            Element elNota = doc.createElement("nota");
            elRoot.appendChild(elNota);
            Attr attr = doc.createAttribute("id");
            attr.setValue(String.valueOf(n.getId()));
            elNota.setAttributeNode(attr);

            Element elTitolo = doc.createElement("titolo");
            elTitolo.appendChild(doc.createTextNode(n.getTitolo()));
            elNota.appendChild(elTitolo);

            Element elGruppo = doc.createElement("gruppo");
            elGruppo.appendChild(doc.createTextNode(n.getGruppo()));
            elNota.appendChild(elGruppo);

            Element elTesto = doc.createElement("testo");
            elTesto.appendChild(doc.createTextNode(n.getTesto()));
            elNota.appendChild(elTesto);

            Element elAggiunta = doc.createElement("aggiunta");
            elAggiunta.appendChild(doc.createTextNode(n.getAggiunta()));
            elNota.appendChild(elAggiunta);

            Element elModifica = doc.createElement("modifica");
            elModifica.appendChild(doc.createTextNode(n.getModifica()));
            elNota.appendChild(elModifica);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult sr = new StreamResult(inputFile);
        transformer.transform(source, sr);
    }
}
