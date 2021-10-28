package model.dao.impl;

import exception.Excessao;
import model.dao.SellerDao;
import model.entity.Department;
import model.entity.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection cnn;
    public SellerDaoJDBC(Connection cnn){
        this.cnn = cnn;
    }



    @Override
    public void insere(Seller obj) {
        /**/
        PreparedStatement prds = null;
        try{
            String sql = "INSERT INTO seller(name, email, birthdate, baseSalary, departmentid) " +
                    "VALUES(?,?,?,?,?);";
            prds = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prds.setString(1,obj.getName());
            prds.setString(2, obj.getEmail());
            prds.setDate(3, new java.sql.Date(obj.getBirthdate().getTime()));
            prds.setDouble(4, obj.getBaseSalary());
            prds.setInt(5, obj.getDepartmen().getId());
            int ln = prds.executeUpdate();

            if(ln > 0){
                ResultSet rs = prds.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
            }else
                throw new Excessao("ERRO, NENHUMA LINHA ALTERADA");
        } catch (SQLException e) {
            throw new Excessao("ERRO DE SQL NA INSERÇÃO :" + e.getMessage());
        }
    }

    @Override
    public void alterar(Seller obj) {
        PreparedStatement prds = null;
        try{

            String sql = "UPDATE seller SET name=?, email=?, birthdate=?, baseSalary=?, departmentid=? " +
                    "WHERE id=?;";

            prds = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prds.setString(1,obj.getName());
            prds.setString(2, obj.getEmail());
            prds.setDate(3, new java.sql.Date(obj.getBirthdate().getTime()));
            prds.setDouble(4, obj.getBaseSalary());
            prds.setInt(5, obj.getDepartmen().getId());
            prds.setInt(6, obj.getId());
            int ln = prds.executeUpdate();

            if(ln > 0){
                ResultSet rs = prds.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
            }else
                throw new Excessao("ERRO, NENHUMA LINHA ALTERADA");
        } catch (SQLException e) {
            throw new Excessao("ERRO DE SQL NA INSERÇÃO :" + e.getMessage());
        }
    }

    @Override
    public void deletarPorId(Integer id) {
        PreparedStatement prds = null;
        try{
            String sql = "DELETE FROM seller WHERE id=?;";
            prds = cnn.prepareStatement(sql);
            prds.setInt(1, id);
            int row = prds.executeUpdate();
            if(row == 0){
                throw new Excessao("NÃO EXISTE OBJ NO BANCO DE DADOS!!!");
            }
        } catch (SQLException e) {
            throw new Excessao(e.getMessage());
        }
    }
    @Override
    public Seller consultar(Integer id) {
        PreparedStatement prds = null;
        ResultSet rs = null;
        try{
            String sql = "select seller .*, department.name as DepName " +
                    "from seller inner join department " +
                    "on seller.DepartmentId = department.id " +
                    "where seller.id = ?;";

            prds = cnn.prepareStatement(sql);
            prds.setInt(1, id);
            rs = prds.executeQuery();

            if(rs.next()){
                Department dep = new Department();
                dep.setId(rs.getInt("departmentid"));
                dep.setName(rs.getString("depname"));
                Seller sel = new Seller();
                sel.setId(rs.getInt("id"));
                sel.setName(rs.getString("name"));
                sel.setEmail(rs.getString("email"));
                sel.setBaseSalary(rs.getDouble("basesalary"));
                sel.setBirthdate(rs.getDate("birthdate"));
                sel.setDepartmen(dep);
                return sel;


            }else {
                System.out.println("VENDEDOR NÃO ENCONTRADO");
                return null;
            }
        } catch (SQLException e) {
            throw new Excessao(e.getMessage());
        }
    }

    @Override
    public List<Seller> listarPorDepartment(Department dep) {
        PreparedStatement prds = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT seller .*, department.name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "on seller.departmentid = department.id " +
                    "WHERE departmentid = ? " +
                    "ORDER BY name;";
            prds = cnn.prepareStatement(sql);
            prds.setInt(1, dep.getId());

            rs = prds.executeQuery();

            List<Seller> lista = new ArrayList<>();

            //NÃO PODEMOS CRIAR UM OBJ DEPARTMENT JÁ CRIADO
            Map<Integer, Department> map = new HashMap<>();


            while(rs.next()){
                Department dp = map.get(rs.getInt("departmentid"));
                if(dp == null){

                    dp = instanciarDepartment(rs);
                    map.put(rs.getInt("departmentid"), dp);
                }
                Seller obj = instanciarSeller(rs, dp);
                lista.add(obj);
            }

            return lista;
        } catch (SQLException e) {
            throw new Excessao(e.getMessage());
        }

    }

    @Override
    public List<Seller> listar() {
        PreparedStatement prds = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT seller .*, department.name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "on seller.departmentid = department.id " +
                    "ORDER BY department.name;";
            prds = cnn.prepareStatement(sql);


            rs = prds.executeQuery();

            List<Seller> lista = new ArrayList<>();

            //NÃO PODEMOS CRIAR UM OBJ DEPARTMENT JÁ CRIADO
            Map<Integer, Department> map = new HashMap<>();


            while(rs.next()){

                Department dp = map.get(rs.getInt("departmentid"));
                if(dp == null){
                    dp = instanciarDepartment(rs);
                    map.put(rs.getInt("departmentid"), dp);
                }
                Seller obj = instanciarSeller(rs, dp);
                lista.add(obj);
            }

            return lista;
        } catch (SQLException e) {
            throw new Excessao(e.getMessage());
        }
    }




    private Department instanciarDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("id"));
        dep.setName(rs.getString("depname"));
        return dep;
    }
    private Seller instanciarSeller(ResultSet rs, Department dep) throws SQLException {
        Seller sel = new Seller();
        sel.setId(rs.getInt("id"));
        sel.setName(rs.getString("name"));
        sel.setEmail(rs.getString("email"));
        sel.setBaseSalary(rs.getDouble("basesalary"));
        sel.setBirthdate(rs.getDate("birthdate"));
        sel.setDepartmen(dep);
        return sel;

    }

}

//OU CRIAR MÉTODO PARA ENCHUGAR O CODIGO E REAPROVEITAR
/*
   //while se lista
    while(rs.next){
        Department dep = instanciarDepartment(rs);
        Seller sel = instanciarSeller(rs, dep);
         lista.add(sel);


    }
       return lista;

       //if se obj
       if(rs.next()){
            Department dep = instanciarDepartment(rs);
            Seller sel = instanciarSeller(rs, dep);
            return sel;
       }

    private Department instanciarDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("departmentid"));
        dep.setName(rs.getString("depname"));
        return dep;
    }
    private Seller instanciarSeller(ResultSet rs, Department dep) throws SQLException {
        Seller sel = new Seller();
        sel.setId(rs.getInt("id"));
        sel.setName(rs.getString("name"));
        sel.setEmail(rs.getString("email"));
        sel.setBaseSalary(rs.getDouble("basesalary"));
        sel.setBirthdate(rs.getDate("birthdate"));
        sel.setDepartmen(dep);
        return sel;

    }*/