package model.dao.impl;

import exception.Excessao;
import model.dao.DepartmentDao;
import model.entity.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {
    private Connection cnn;
    public DepartmentDaoJDBC(Connection cnn) {
        this.cnn = cnn;
    }

    @Override
    public void insere(Department obj) {
        PreparedStatement prds = null;
        try{
            String sql = "INSERT INTO department(id, name) VALUES (?,?);";
            prds = cnn.prepareStatement(sql);
            prds.setInt(1, obj.getId());
            prds.setString(2, obj.getName());
            prds.executeUpdate();


        } catch (SQLException e) {
            throw new Excessao(e.getMessage());
        }
    }

    @Override
    public void alterar(Department obj, int id) {
        PreparedStatement prds = null;
        try{
            String sql = "UPDATE department SET name=?, id=? WHERE id=?;";
            prds = cnn.prepareStatement(sql);
            prds.setString(1, obj.getName());
            prds.setInt(2, obj.getId());
            prds.setInt(3, id);
            int row = prds.executeUpdate();

            if(row > 0){
                System.out.println("LINHA ALTERADA");
            }else{
                throw new Excessao("Não Existe Department!!!!!");
            }
        } catch (SQLException e) {
            throw new Excessao(e.getMessage());
        }
    }

    @Override
    public void deletarPorId(Integer id) {
        PreparedStatement prds = null;
        try{
            String sql = "DELETE FROM department WHERE id=?;";
            prds = cnn.prepareStatement(sql);
            prds.setInt(1, id);
            prds.executeUpdate();

        } catch (SQLException e) {
            throw new Excessao("NÃO PODE DELETAR O DEPARTMENT 'ele' PODE POSSUIR DEPENDENCIAS\n\n" + e.getMessage());
        }
    }

    @Override
    public Department consultar(Integer id) {
        PreparedStatement prds = null;
        ResultSet rs = null;
        Department department = new Department();
        try{
            String sql = "SELECT *FROM department WHERE id=?;";
            prds = cnn.prepareStatement(sql);
            prds.setInt(1,id);
             rs = prds.executeQuery();

            if(rs.next()){
                department.setId(rs.getInt("id"));
                department.setName(rs.getString("name"));
            }

        } catch (SQLException e) {
            throw new Excessao(e.getMessage());
        }

        return department;
    }

    @Override
    public List<Department> listar() {
        ResultSet rs = null;
        Statement st = null;
        List<Department>lista = new ArrayList<>();
        try{
            String sql = "SELECT *FROM department;";
            st = cnn.createStatement();
            rs = st.executeQuery(sql);

            while(rs.next()){
                Department dp = new Department();
                dp.setName(rs.getString("name"));
                dp.setId(rs.getInt("id"));
                lista.add(dp);
            }


        } catch (SQLException e) {
            throw new Excessao(e.getMessage());
        }

        return lista;
    }
}
