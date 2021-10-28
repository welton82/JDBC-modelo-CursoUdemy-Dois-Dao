package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entity.Department;
import model.entity.Seller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) throws ParseException {

        //consultar(1);
        //listarSellerPorDepartment(4);
        //listarSeller();
        //inserirSeller();
        //atualizarSeller();
        //deletarSeller();

        //listarDepartment();
        //consultarDepartment();
        //alterarDepartment();
        //deletarDepartment();
        //inserirDepartment();

    }
    //métodos dos crud
    public static void consultarSeller(int id){
        //CRIAR CONEXAO E ENCONTRAR POR ID
        SellerDao sellerDao = DaoFactory.criarSellerDao();
        Seller seller = sellerDao.consultar(id);
        System.out.println(seller);
    }
    //Não cria obj (Department) repetidos
    public static void listarSellerPorDepartment(int id){
        SellerDao dao = DaoFactory.criarSellerDao();
        Department dp = new Department(id, "");
        List<Seller> list = dao.listarPorDepartment(dp);

        for(Seller x:list){
            System.out.println(x);
        }
    }
    //FIND ALL sem restrição
    public static void listarSeller(){
        SellerDao dao = DaoFactory.criarSellerDao();
        List<Seller> list = dao.listar();

        for(Seller x:list){
            System.out.println(x);
        }
    }
    public static void inserirSeller() throws ParseException {
        SellerDao dao = DaoFactory.criarSellerDao();
        Department dp = new Department(4,"Eletronicos");
        Seller sel = new Seller(null, "Matheus Ferreira","24/07/2000",4600.22,dp,"mateu@gmail.com");
        dao.insere(sel);

    }
    public static void atualizarSeller() throws ParseException {
        SellerDao dao = DaoFactory.criarSellerDao();

        Seller sel = dao.consultar(1);
        sel.setName("Pereira");

        dao.alterar(sel);
    }
    public static void deletarSeller(){
        SellerDao dao = DaoFactory.criarSellerDao();
        dao.deletarPorId(35);

    }


    public static void listarDepartment(){
        DepartmentDao dao = DaoFactory.criarDepartmentDao();
        List<Department>listar = dao.listar();
        for(Department x:listar){
            System.out.println(x);
        }

    }
    public static void consultarDepartment(){
        DepartmentDao dao = DaoFactory.criarDepartmentDao();
        Department dp = dao.consultar(2);
        System.out.println("DP CONSULTADO: " + dp);
    }
    public static  void alterarDepartment(){
        DepartmentDao dao = DaoFactory.criarDepartmentDao();
        Department d = new Department(5,"Home");
        dao.alterar(d, 7);

    }
    public static void deletarDepartment(){
        DepartmentDao dao = DaoFactory.criarDepartmentDao();
        dao.deletarPorId(0);

    }
    public static void inserirDepartment(){
        DepartmentDao dao = DaoFactory.criarDepartmentDao();
        Department dp = new Department(6, "Testes 3");
        dao.insere(dp);

    }
}

















