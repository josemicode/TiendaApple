package tiendaApple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import accesorios.accesorio;
import accesorios.airPods;
import accesorios.complemento;
import accesorios.iWatch;
import dispositivos.Mac;
import dispositivos.dispositivo;
import dispositivos.iPad;
import dispositivos.iPhone;



public class tiendaApple {

	public static void main(String[] args) throws IOException {
		
		List<dispositivo> listaiPad = leerDispositivos(new File("./src/iPad.txt"), "ipad");
		List<dispositivo> listaiPhone = leerDispositivos(new File("./src/iPhone.txt"), "iphone");
		List<dispositivo> listaMac = leerDispositivos(new File("./src/Mac.txt"), "mac");
		
		List<complemento> listaairPods = leerComplementos(new File("./src/airPods.txt"), "airpods");
		List<complemento> listaiWatch = leerComplementos(new File("./src/iWatch.txt"), "iwatch");
		List<complemento> listaAccesorio = leerComplementos(new File("./src/accesorio.txt"), "accesorio");		
		
		
		String fullprint = ("Bienvenido a Apple, disfrute de una seleccion de productos criticamente aclamados: \n\n\n"
				+ "El iPad - la mejor tablet del mundo \n\n"
				+ printDispositivos(listaiPad) + "\n\n"
				+ "El iPhone - el telefono de los artistas \n\n"
				+ printDispositivos(listaiPhone) + "\n\n"
				+ "MacBook - toda la potencia en un portatil \n\n"
				+ printDispositivos(listaMac)
				+ "\n\n				//---------------------------//\n\n\n"
				+ "Los complementos que mejoraran tu experiencia Apple: \n\n\n"
				+ printComplementos(listaairPods) + printComplementos(listaiWatch) + printComplementos(listaAccesorio));
		
		File stock = new File("./src/stock.txt");
		stock.createNewFile();
		
		FileWriter fw = new FileWriter(stock);
		fw.write(fullprint);
		fw.close();
				
		List<dispositivo> listaPedido = new ArrayList<>();
		listaPedido.add(listaiPad.get(5));
		listaPedido.add(listaiPhone.get(2));
		listaPedido.add(listaMac.get(0));
		
		List<complemento> listaPedido2 = new ArrayList<>();
		listaPedido2.add(listaairPods.get(2));
		listaPedido2.add(listaiWatch.get(1));
		
		List<complemento> listaSugerencias = new ArrayList<>();
		listaSugerencias.addAll(listaairPods);
		listaSugerencias.addAll(listaiWatch);
		listaSugerencias.addAll(listaAccesorio);
		
		printPedido(listaPedido, listaPedido2, listaSugerencias);
	}
	
	public static void printPedido(List<dispositivo> pedidoDispositivos, List<complemento> pedidoComplementos, List<complemento> listaSugerencias) {
		String recibo = "Aqui tiene su recibo:			Recomendaciones:\n";
		double total = 0;
		
		for(dispositivo e : pedidoDispositivos) {
			List<complemento> recomendaciones = e.compatible(listaSugerencias);
			recibo+=("\n" + e.getModelo() + " - " + e.getPrecio() + "$			//"+recomendaciones.toString());
			total+=e.getPrecio();
		}
		
		if(pedidoComplementos!=null) {
			for(complemento e : pedidoComplementos) {
				recibo+=("\n" + e.getModelo() + " - " + e.getPrecio() + "$");
				total+=e.getPrecio();
			}
		}
		
		recibo+=("\n\n-->Total: " + total + "$"
				+ "\n\n//------------------------------//");
		
		//System.out.println(recibo);
		
		File pedido = new File("./src/recibo.txt");
		
		try {
			pedido.createNewFile();
			FileWriter fw = new FileWriter(pedido);
			fw.write(recibo);
			fw.close();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public static List<complemento> leerComplementos(File ar, String type) throws IOException {
		List<complemento> res = new ArrayList<>(); 
		
		LineNumberReader linenumber = new LineNumberReader(new FileReader(ar));
		linenumber.skip(Integer.MAX_VALUE);
		
		BufferedReader br = new BufferedReader(new FileReader(ar));
		
		
		for(int i = 0; i < linenumber.getLineNumber(); i++) {
			
			String aux = br.readLine();
			res.add(comp_objType(type));
			
			StringTokenizer tok = new StringTokenizer(aux, "-");
			
			res.get(i).setModelo(tok.nextToken());
			
			res.get(i).setPrecio(Double.parseDouble(tok.nextToken()));
			
			StringTokenizer tik = new StringTokenizer(tok.nextToken(), "_");
			List<String> compatibles = new ArrayList<>();
			
			while(tik.hasMoreTokens()) {
				compatibles.add(tik.nextToken());
			}
			
			res.get(i).setCompIds(compatibles);
			
		}
		
		
		return res;
	}
	
	public static String printComplementos(List<complemento> comp) {
		String res = "";
		
		for(int i = 0; i < comp.size(); i++) {
			res += comp.get(i).toString() + "\n";
		}
		return res;
	}
	
	
	public static complemento comp_objType(String type) {
		complemento res = null;
		
		switch(type) {
		case "iwatch":
			res = new iWatch(null, 0, null);
			break;
		case "airpods":
			res = new airPods(null, 0, null);
			break;
		case "accesorio":
			res = new accesorio(null, 0, null);
			break;
		}
		
		return res;
	}
	
	public static List<dispositivo> leerDispositivos(File ar, String type) throws IOException {
		List<dispositivo> res = new ArrayList<>(); 
		
		LineNumberReader linenumber = new LineNumberReader(new FileReader(ar));
		linenumber.skip(Integer.MAX_VALUE);
		
		BufferedReader br = new BufferedReader(new FileReader(ar));
		
		
		for(int i = 0; i < linenumber.getLineNumber(); i++) {
			
			String aux = br.readLine();
			res.add(disp_objType(type));
			
			StringTokenizer tok = new StringTokenizer(aux, "-");
			
			res.get(i).setModelo(tok.nextToken());
			
			res.get(i).setPrecio(Double.parseDouble(tok.nextToken()));
			
			res.get(i).setId(tok.nextToken());
			
			res.get(i).setProcesador(tok.nextToken());;
			
		}
		
		
		return res;
	}
	
	public static String printDispositivos(List<dispositivo> disp) {
		String res = "";
		
		for(int i = 0; i < disp.size(); i++) {
			res += disp.get(i).toString() + "\n";
		}
		return res;
	}
	
	public static dispositivo disp_objType(String type) {
		dispositivo res = null;
		
		switch(type) {
		case "ipad":
			res = new iPad(null, 0, null, null);
			break;
		case "iphone":
			res = new iPhone(null, 0, null, null);
			break;
		case "mac":
			res = new Mac(null, 0, null, null);
			break;
		}
		
		return res;
	}

}
