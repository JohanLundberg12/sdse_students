package org.nypl.journalsystem;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LibrarySystem {

	List<Journal> journalList = new ArrayList<>();
	List<Author> authorList = new ArrayList<>();

	public LibrarySystem() {
		//TODO: Initialize system with default journals.
		Publisher springer = new Publisher("Springer", "Germany");
		Publisher elsevier = new Publisher("Elsevier", "Netherlands");
		Publisher natureResearch= new Publisher("Nature Research", "Great Britain");

		Journal higherEducation = new Journal("Higher Education", springer, "0018-1560");
		Journal system = new Journal("System", elsevier, "0346-2511");
		Journal chem = new Journal("Chem", elsevier, "2451-9294");
		Journal nature = new Journal("Nature Research", natureResearch, "1476-4687");
		Journal society = new Journal("Society", springer, "0147-2011");

		this.journalList.add(higherEducation);
		this.journalList.add(system);
		this.journalList.add(chem);
		this.journalList.add(nature);
		this.journalList.add(society);
	}
	
	public void load() throws IOException {
		loadAuthors();
		loadArticles();
	}
	
	protected void loadAuthors() throws IOException {
		File file = new File("data/Authors.csv");
		String[] headers = {"ID","Name"};
		Reader in = new FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT
				.withHeader(headers)
				.withFirstRecordAsHeader()
				.parse(in);
		for (CSVRecord record : records) {
			String id = record.get(0);
			String last = record.get(1);
			String first = record.get(2);
			String name = last + "," + first;
			Author author = new Author(id, name);
			authorList.add(author);
		}
		//TODO: Load authors from file
	}
	
	protected void loadArticles() throws IOException {
		File file = new File("data/Articles.csv");
		String[] headers = {"ID","Title","AuthorIDs","ISSN"};
		Reader in = new FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT
				.withHeader(headers)
				.withFirstRecordAsHeader()
				.parse(in);
		for (CSVRecord record : records) {
			String title = record.get(1);
			String IDs = record.get(2);
			String issn = record.get(3).replace(" ","");
			List<Author> authors = new ArrayList<>();
			List<String> mylist = new ArrayList<>(Arrays.asList((IDs.substring(2,IDs.length()-1)).split(";")));
			for (int i = 0; i < mylist.size();i++) {
				mylist.set(i,mylist.get(i).trim());
			}
			for (Author author : authorList) {
				if (mylist.contains(author.id)){
					authors.add(author);
				}
			}

			Article article = new Article(title, authors);
			for (Journal journal : journalList) {
				if (issn.equals(journal.ISSN)) {
					journal.articleList.add(article);
					if (journal.articleList.size() >= 3) {
						journal.fullissue = true;
					}
					break;
				}


			}
		}

		//TODO: Load articles from file and assign them to appropriate journal
	}
	
	
	public void listContents() {
		//TODO: Print all journals with their respective articles and authors to the console.
		for (Journal journal : journalList) {
			System.out.println(journal);
		}
	}
	
	public static void main(String[] args) throws Exception {
		LibrarySystem librarySystem = new LibrarySystem();
		
		librarySystem.load();
		librarySystem.listContents();
	}
}
