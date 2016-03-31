package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eee on 30.03.2016.
 */
public class ContactDataGenerator {


    @Parameter(names = "-c", description = "Contact count")
    int count;

    @Parameter(names = "-f", description = "Target file")
    String file;

    @Parameter(names = "-d", description = "Data format")
    public String format;


    public static void main(String[] args) throws IOException {
      ContactDataGenerator generator = new ContactDataGenerator();
      JCommander jCommander = new JCommander(generator);
      try {
        jCommander.parse(args);
      } catch (ParameterException ex) {
        jCommander.usage();
        return;
      }
    /*int count = Integer.parseInt(args[0]);
    File file = new File(args[1]);*/
      generator.run();


    }

    private void run() throws IOException {
      List<ContactData>contacts= generateContacts(count);
      if (format.equals("csv"))
        saveCsv(contacts, new File(file));
      else if (format.equals("xml"))
        saveXml(contacts, new File(file));
      else if (format.equals("json"))
        saveJson(contacts, new File(file));
      else System.out.println("Unrecognized format " + format);
    }

    private void saveJson(List<ContactData> contacts, File file) throws IOException {
      Gson gson = new GsonBuilder().setPrettyPrinting().
              excludeFieldsWithoutExposeAnnotation().create();
      String json = gson.toJson(contacts);
      try (Writer writer = new FileWriter(file)) {
        writer.write(json);  // writer.close();
      }

    }


    private void saveXml(List<ContactData> contacts, File file) throws IOException {
      XStream xstream = new XStream();
      //xstream.alias("group",GroupData.class);
      xstream.processAnnotations(ContactData.class);
      String xml = xstream.toXML(contacts);
      Writer writer = new FileWriter(file);
      writer.write(xml);
      writer.close();
    }


    private void saveCsv(List<ContactData> contacts, File file) throws IOException {
      if (file.exists()){
        System.out.println(" The file already exists");
        return;
      }
      try (Writer writer = new FileWriter(file)) {
        for (ContactData contact : contacts) {
          writer.write(String.format("%s;%s;%s\n", contact.getFirstName(),
                  contact.getLastName(), contact.getAddress()));
        }
        //    writer.close();
      }
    }

    private List<ContactData> generateContacts(int count) {
      List<ContactData> contacts = new ArrayList<ContactData>();
      for (int i = 0; i < count; i++)
        contacts.add(new ContactData().withFirstname(String.format("Olga %s", i)).
                withLastname(String.format("Ivanova %s", i)).
                withAddress(String.format("51 Red Square %s", i)));
      return contacts;
    }

  }
