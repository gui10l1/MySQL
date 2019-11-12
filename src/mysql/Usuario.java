package mysql;

public class Usuario {
    private String email;
    private String senha;
    
    
    //Email
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getEmail(){
        return email;
    }
    
    //Senha
    public void setSenha(String senha){
        this.senha = senha;
    }
    
    public String getSenha(){
        return senha;
    }
}
