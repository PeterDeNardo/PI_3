
package pi_3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AEstrela {
    public static List<MappedTile> listaFechada = new ArrayList();
    public static List<MappedTile> listaAberta = new ArrayList();
    public static List<MappedTile> caminho = new ArrayList();    
    public static int colunasDoMapa = 0;
    public static int linhasDoMapa = 0;
    public static int tamanhoDoMapa = 0;
   
    public static List<MappedTile> aEstrela(MappedTile tileInicial, MappedTile tileDestino, ViewRange map){
        colunasDoMapa = map.getColunas();
        linhasDoMapa = map.getLinhas();
        tamanhoDoMapa = map.getMap().size();
        
        listaFechada.clear();
        listaAberta.clear();
        caminho.clear();
        boolean achouCaminho = false;
    
        MappedTile noAtual = tileInicial;
        listaAberta.add(tileInicial);
        
        while(!achouCaminho)
        {
            noAtual = procularMenorF();
            listaAberta.remove(noAtual);
            listaFechada.add(noAtual);
            achouCaminho = noAtual.equals(tileDestino);
            for(MappedTile no: noAtual.getVizinhos())
            {
                if(no.estaBloqueado() || listaFechada.contains(no))
                {
                    continue;
                }else{
                    if(!listaAberta.contains(no))
                    {
                        System.out.println(no.getId());
                        listaAberta.add(no);
                        no.setPai(noAtual);
                        no.setH(calcularH(no, tileDestino));
                        no.setG(calcularG(no, noAtual));
                        no.setF(calcularF(no));
                    }else{
                        if(no.getG()<noAtual.getG())
                        {
                            no.setPai(noAtual);
                            no.setG(calcularG(noAtual, no));
                            no.setF(calcularF(no));
                        }
                    
                    }
                }
            
            }
            if(listaAberta.isEmpty())
            {
                System.out.println("Não achou ");
                return null;
            }
        }
        
        return montaCaminho(tileInicial, tileDestino, map);
    }
    
    public static MappedTile procularMenorF() {
        Collections.sort(listaAberta, Comparator.comparing(MappedTile::getF));
        return listaAberta.get(0);
        
    }
    
    public static float calcularF(MappedTile no)
    {
        return no.getG()+ no.getH();

    }
    
    public static float calcularG(MappedTile noAtual, MappedTile noVizinho)
    {
        if (noVizinho.getId() % colunasDoMapa == noAtual.getId() % colunasDoMapa || noVizinho.getId() + 1 == noAtual.getId() || noVizinho.getId() - 1 == noAtual.getId()) {
            return noVizinho.getG() + 10;
        } else {
            return noVizinho.getG() + 14;
        }
        
    }
    
    public static float calcularH(MappedTile noAtual, MappedTile noDestino)
    {
        int posicaoDestinoX = (noDestino.getId()%colunasDoMapa)+1;
        int posicaoNoAtualX = (noAtual.getId()%colunasDoMapa)+1;
        
        int distanciaX = posicaoDestinoX > posicaoNoAtualX ? posicaoDestinoX - posicaoNoAtualX : posicaoNoAtualX - posicaoDestinoX;
        
        int posicaoDestinoY = (noDestino.getId()/linhasDoMapa)+1;
        int posicaoNoAtualY = (noAtual.getId()/linhasDoMapa)+1;
        
        int distanciaY = posicaoDestinoY > posicaoNoAtualY ? posicaoDestinoY - posicaoNoAtualY : posicaoNoAtualY - posicaoDestinoY;
        
        float distanciaTotal = (float)Math.sqrt((Math.pow(distanciaX, 2)+Math.pow(distanciaY, 2)))*10;
                
        return distanciaTotal;
    }
    
    private static List<MappedTile> montaCaminho(MappedTile noInicial, MappedTile noDestino, ViewRange map) {
        List<MappedTile> listaAuxiliar = new ArrayList();
        MappedTile noAtual = noDestino;
        int contador = 0;
        while (!listaAuxiliar.contains(noInicial) || contador > tamanhoDoMapa)
        {
            listaAuxiliar.add(noAtual);
            
            noAtual = noAtual.getPai();
                        
            contador++;
        }
        Collections.reverse(listaAuxiliar);
        
        
        //imprimir caminho
        System.out.println("Caminho: ");
        for(MappedTile no: listaAuxiliar)
        {
            System.out.print(" -> " + no.getTileId());
        }
        //inicio artificio apenas para printar caminho
        for(MappedTile no: map.getMap())
        {
          if(!listaAuxiliar.contains(no))  no.setPai(null);
          
        }
        //fim do artificio
        
        System.out.println("");
        desenha(map);
        System.out.println("Fim ! ");
        
        //retorno do caminho
        return listaAuxiliar;
    }
    
    public static void desenha(ViewRange map){
      System.out.println("");
      for (int i = 0; i<map.getLinhas(); i++)
        {
            for (int j = 0; j<map.getColunas(); j++)
            {
              MappedTile no = map.getMap().get((i*map.getColunas())+j);
              if(no.getPai() != null ){
                System.out.print("[-]");
              }else if(no.estaBloqueado()){
                System.out.print("[X]");
              }else{
                System.out.print("[ ]");
              }
            }
             System.out.println();
        }
    }
    
}

