public class bebida extends Producto{
    public bebida(String nombre, double precio){
        super(nombre, precio);

    }
    @Override
    public String obtenerDetalleTipo(){
        return "Categoria: bebida";
    }
}
