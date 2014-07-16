/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.ifes.edu.br.control;



import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tiago
 */
public class ImageProcessor {
    
    
    private final int INICIO =1;
    private final int FIM =17;
            
            
    
    private List<Imagem2> imagens;
    
    public ImageProcessor(){
        imagens = new ArrayList();
    }
    
    
    public void carregarBancoImagens() {
        
        try{
            
            ThreadGroup threadPool = new ThreadGroup("imageExtractors");
            
            for(int i=INICIO;i<FIM;i++){
                
                //ImageExtractorThread imageExtractorThread = new ImageExtractorThread(threadPool,imagens,""+i);
                //imageExtractorThread.start();
                
            }
            
            while(threadPool.activeCount()>0){
                //aguarda 2 segundos e verifica novamente o numero de threads ativas;
                Thread.sleep(2000);
                //System.out.println("Numero de Threads Ativas: "+threadPool.activeCount());
            }
            
            System.out.println("Concluido!!");
        
        }
        catch(Exception e){
            System.out.println("Erro: "+e.getMessage());
        }
        
    }

    private void buscarImage(int img){
        try{
          
            ImageMatcherThread imageMatcherThread = new ImageMatcherThread(imagens, ""+img);
            imageMatcherThread.start();
            
        }
        catch(Exception e){
            System.out.println("ERRO! "+e.getMessage());
        }
    }
    
    
    public void realizarBusca(){
        int opcao;
        
        
        //ThreadGroup threadPool = new ThreadGroup("imageExtractors");
            
        
        for(int i=17;i<33;i++){
            opcao=i;
            System.out.print(i+" ");
            /*
            do{
                System.out.print("Numero da Imagem [17-32]\n ou -1 para sair: ");
                Scanner scanner = new Scanner(System.in);
                opcao = scanner.nextInt();
                if(opcao==-1)
                    break;
            }while(opcao<17||opcao>32);
            
            if(opcao==-1)
                    break;
            */
            buscarImage(opcao);
            
        }
        
    }
    
    
}
