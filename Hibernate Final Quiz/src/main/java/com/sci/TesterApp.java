package com.sci;

import com.sci.criteria.FilterQuery;
import com.sci.criteria.Operator;
import com.sci.dao.DBAuthor;
import com.sci.dao.DBBook;
import com.sci.dao.DBConfig;
import com.sci.models.*;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TesterApp {
    public static void main(String[] args) {
        DBBook bookDB = new DBBook();
        DBAuthor authorDB = new DBAuthor();

        //Run these insertions in tables for 1 time:
//        Author author1 = new Author();
//        author1.setId(1);
//        author1.setName("J. K. Rowling");
//        author.create(author1);

//        Book book1 = new Book(1,"HarryPotter", Date.valueOf("2011-01-01"));
//        Book book2 = new Book(2,"The Lord Of The Rings",
//                Date.valueOf("2006-03-15"));
//        bookDB.create(book1);
//        bookDB.create(book2);

        List<Author> authors = authorDB.get();
        for (Author author : authors) {
            System.out.println(author);
        }
        List<FilterQuery> filters = new ArrayList<>();
        filters.add(new FilterQuery("id", "1",
                Operator.GreaterThan));
        filters.add(new FilterQuery("publishedAt",
                Date.valueOf("2005-12-31"), Operator.GreaterThan));
        filters.add(new FilterQuery("name",
                "The Lord Of The Rings", Operator.Equal));
        List<Book> books = bookDB.getByFilter(filters);
        for (Book book : books) {
            System.out.println(book);
        }
        DBConfig.shutdown();
    }
}
