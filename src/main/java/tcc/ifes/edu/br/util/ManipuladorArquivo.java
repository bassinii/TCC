/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc.ifes.edu.br.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author hidrom
 */
public class ManipuladorArquivo {

    private InputStream is;
    private InputStreamReader fileReader;
    private BufferedReader entrada;

    private FileWriter fileWriter;
    private BufferedWriter saida;

    public void IniciarLeituraArquivo(InputStream readerFile) throws FileNotFoundException {
        is = readerFile;
        
        fileReader = new InputStreamReader(is);
        entrada = new BufferedReader(fileReader);
    }

    public void IniciarEscritaArquivo(File writeFile) throws FileNotFoundException, IOException, Exception {

        fileWriter = new FileWriter(writeFile, true);
        saida = new BufferedWriter(fileWriter);
    }
    
    public void IniciarEscritaArquivoNovo(File file) throws Exception {
        fileWriter = new FileWriter(file, false);
        saida = new BufferedWriter(fileWriter);
    }

    public String lerLinha() throws IOException {
        
        
        
        return entrada.readLine();
    }

    public void escreverLinha(String linha) throws IOException {
        
        saida.append(linha);
        saida.newLine();
        saida.flush();
        
    }

    public void fecharArquivoLeitura() {
        try {
            entrada.close();
            fileReader.close();
            is.close();
        } catch (Exception e) {
            System.out.println("Erro ao encerrar Arquivo de Leitura!");
        }
    }

    public void fecharArquivoEscrita() {
        try {
            saida.flush();
            saida.close();
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Erro ao encerrar Arquivo de Leitura!");
        }
    }

}
