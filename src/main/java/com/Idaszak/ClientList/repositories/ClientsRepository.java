package com.Idaszak.ClientList.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.Idaszak.ClientList.models.Client;

@Repository
public class ClientsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Client> getClients() {
        var clients = new ArrayList<Client>();


        String sql = "SELECT * FROM clients ORDER BY id DESC";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);


        while(rows.next()){
            Client client = new Client();
            client.setId(rows.getInt("id"));
            client.setFirstName(rows.getString("firstname"));
            client.setLastName(rows.getString("lastname"));
            client.setEmail(rows.getString("email"));
            client.setPhone(rows.getString("phone"));
            client.setAddress(rows.getString("address"));
            client.setCreatedAt(rows.getString("created_at"));

            clients.add(client);
        }


        return clients;
    }


    public class Page {
        public List<Client> clients;
        public int totalPages;
        public int pageIndex;
    }


    public Page getClients(int pageIndex, int pageSize) {
        var clients = new ArrayList<Client>();


        // get number of clients
        String sqlCount = "SELECT COUNT(*) FROM clients";
        int numClients = jdbcTemplate.queryForObject(sqlCount, Integer.class);


        String sql = "SELECT * FROM clients ORDER BY id DESC LIMIT ? OFFSET ?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, pageSize
                , (pageIndex - 1) * pageSize);


        while(rows.next()){
            Client client = new Client();
            client.setId(rows.getInt("id"));
            client.setFirstName(rows.getString("firstname"));
            client.setLastName(rows.getString("lastname"));
            client.setEmail(rows.getString("email"));
            client.setPhone(rows.getString("phone"));
            client.setAddress(rows.getString("address"));
            client.setCreatedAt(rows.getString("created_at"));

            clients.add(client);
        }


        var page = new Page();
        page.clients = clients;
        page.totalPages = (int) Math.ceil((double) numClients / pageSize);
        page.pageIndex = pageIndex;

        return page;
    }



    public Client getClient(int id) {
        String sql = "SELECT * FROM clients WHERE id=?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, id);

        if(rows.next()){
            Client client = new Client();
            client.setId(rows.getInt("id"));
            client.setFirstName(rows.getString("firstname"));
            client.setLastName(rows.getString("lastname"));
            client.setEmail(rows.getString("email"));
            client.setPhone(rows.getString("phone"));
            client.setAddress(rows.getString("address"));
            client.setCreatedAt(rows.getString("created_at"));

            return client;
        }

        return null;
    }



    public Client getClient(String email) {
        String sql = "SELECT * FROM clients WHERE email=?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, email);

        if(rows.next()){
            Client client = new Client();
            client.setId(rows.getInt("id"));
            client.setFirstName(rows.getString("firstname"));
            client.setLastName(rows.getString("lastname"));
            client.setEmail(rows.getString("email"));
            client.setPhone(rows.getString("phone"));
            client.setAddress(rows.getString("address"));
            client.setCreatedAt(rows.getString("created_at"));

            return client;
        }

        return null;
    }


    public Client createClient(Client client) {
        String sql = "INSERT INTO clients (firstname, lastname, email, phone, " +
                "address, created_at) VALUES (?, ? , ?, ?, ?, ?)";

        int count = jdbcTemplate.update(sql, client.getFirstName(), client.getLastName()
                , client.getEmail(), client.getPhone(), client.getAddress()
                , client.getCreatedAt());

        if (count > 0) {
            int id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            return getClient(id);
        }


        return null;
    }


    public Client updateClient(Client client) {
        String sql = "UPDATE clients SET firstname=?, lastname=?, email=?, " +
                "phone=?, address=?, created_at=? WHERE id=?";

        jdbcTemplate.update(sql, client.getFirstName(), client.getLastName()
                , client.getEmail(), client.getPhone(), client.getAddress()
                , client.getCreatedAt(), client.getId());

        return getClient(client.getId());
    }


    public void deleteClient(int id) {
        String sql = "DELETE FROM clients WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}
