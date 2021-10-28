package model.dao;

import connection.Conexao;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;
//CLASSE AUXILIAR RESPONSÁVEL POR INSTANCIAR OS DAOS
public class DaoFactory {

    public static SellerDao criarSellerDao(){
        return new SellerDaoJDBC(Conexao.getConnection());
    }

    public static DepartmentDao criarDepartmentDao(){
        return new DepartmentDaoJDBC(Conexao.getConnection());
    }
}
