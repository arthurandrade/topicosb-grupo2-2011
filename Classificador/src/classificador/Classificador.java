/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classificador;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * @author Sergio
 */
public class Classificador {

    private YamlReader reader;
    private List<String> listaCorpos;
    private List<String> listaPalavras;
    private BufferedWriter out;

    /**
     * @param args the command line arguments
     */
    public Classificador() {
        listaCorpos = new ArrayList<String>();
        listaPalavras = new ArrayList<String>();
        criarDicionario();
        
    }

    public void criarDicionario() {
        String corpo;

        try {
            reader = new YamlReader(new FileReader("training-docs.yml"));
            while (true) {
                Object object = reader.read();
                Map map = (Map) object;
                corpo = map.get("corpo").toString();
                corpo = trataTexto(corpo);
                listaCorpos.add(corpo.toLowerCase());
             }
        } catch (YamlException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            //ex.printStackTrace();
        }

        criarTokensPalavras(listaCorpos, listaPalavras);
        listaPalavras = removePalavrasRepetidas(listaPalavras);      
        criarArquivoDicionario();              
        System.out.println("\n\nExistem " + listaPalavras.size() + " palavras unicas.");
    }
    

    public List<String> removePalavrasRepetidas(List<String> lista) {
        List<String> listaAux = lista;
        HashSet hs = new HashSet();

        hs.addAll(listaAux);
        listaAux.clear();
        listaAux.addAll(hs);

        return listaAux;
    }

    private String trataTexto(String corpo) {
        String texto;

        texto = corpo.replace("¾", " ")
                     .replace("§", " ")
                     .replace("½", " ")
                     .replace("¼", " ")
                     .replace("°", " ")
                     .replace("·", " ")
                     .replace("¬", " ")
                     .replace("ª", " ")
                     .replace("|", " ")
                     .replace("0", " ")
                     .replace("1", " ")
                     .replace("2", " ")
                     .replace("3", " ")
                     .replace("4", " ")
                     .replace("5", " ")
                     .replace("6", " ")
                     .replace("7", " ")
                     .replace("8", " ")
                     .replace("9", " ")
                     .replace("©", " ")
                     .replace("</p>", " ")
                     .replace("<p>", " ")
                     .replace("<p/>", " ")
                     .replace("<strong>", " ")
                     .replace("</strong>", " ")
                     .replace("<html>", " ")
                     .replace("</html>", " ")
                     .replace("<body>", " ")
                     .replace("</body>", " ")
                     .replace("<ol>", " ")
                     .replace("<ul>", " ")
                     .replace("<li>", " ")
                     .replace("</ol>", " ")
                     .replace("</ul>", " ")
                     .replace("</li>", " ")
                     .replace("\"", " ")
                     .replace("=", " ")
                     .replace("”", " ")
                     .replace("“", " ")
                     .replace("</head>", " ")
                     .replace("<em>", " ")
                     .replace("•", " ")
                     .replace("-", " ")
                     .replace("/", " ")
                     .replace("\\", " ")
                     .replace("*", " ")
                     .replace("%", " ")
                     .replace("#", " ")
                     .replace("@", " ")
                     .replace("$", " ")
                     .replace("?", " ")
                     .replace("!", " ")
                     .replace("</em>", " ")
                     .replace(".", " ")
                     .replace(",", " ")
                     .replace(";", " ")
                     .replace(":", " ")
                     .replace("(", " ")
                     .replace(")", " ")
                     .replace("+", " ")
                     .replace("&", " ")
                     .replace("¨", " ")
                     .replace("~", " ")
                     .replace("'", " ")
                     .replace("[", " ")
                     .replace("]", " ")
                     .replace("{", " ")
                     .replace("<", " ")
                     .replace(">", " ")
                     .replace("{", " ")
                     .replace("}", " ")                     
                     .replace("`", " ")
                     .replace("º", " ")
                     .replace("^", " ")
                     .replace("ª", " ")
                     .replace("‘", " ")
                     .replace("’", " ");
 
        return texto;
    }
    
    private void criarArquivoDicionario(){
        FileWriter fstream;

        try {
            fstream = new FileWriter("dicionario.txt");
            out = new BufferedWriter(fstream);
            
            for(String palavra : listaPalavras){
                out.write(palavra + "\n");
            }
            
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void criarTokensPalavras(List<String> listaCorpos, List<String> listaPalavras) {
        Iterator<String> itCorpos = listaCorpos.iterator();
        StringTokenizer str;
        String palavra;

        while (itCorpos.hasNext()) {
            str = new StringTokenizer(itCorpos.next());

            while (str.hasMoreTokens()) {
                palavra = str.nextToken();
                
                if(palavra.length() > 3){
                    listaPalavras.add(palavra);
                }
            }
        }
    }
 
}
