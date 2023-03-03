package client.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// 안드로이드로 사진 전송을 하려면 JavaSE의 Http 통신을 다룰줄 알아야 한다
public class RegistForm extends JFrame {
	
	JTextField t_category_idx;
	JTextField t_product_name;
	JTextField t_brand;
	JTextField t_price;
	JTextField t_discount;
	JTextArea t_detail;
	JButton bt_file; // 파일 탐색기 띄우기
	JButton bt_regist; // 서버에 전송하기 
	
	JFileChooser chooser; // 파일 탐색기
	
	// Http 통신을 위한 객체
	HttpURLConnection con;
	String host = "http://172.30.1.56:7777/admin/rest/product";
	String hypen="--";
	String boundary="*********"; // 하이픈으로 감쌀 데이터의 경계기준 문자열
	String line="\r\n";
	
	// 유저가 전송을 위해 선택한 파일
	File file;
	
	public RegistForm() {
	
		t_category_idx = new JTextField("1", 25);
		t_product_name = new JTextField(25);
		t_brand = new JTextField(25);
		t_price = new JTextField("250000", 25);
		t_discount = new JTextField("195000", 25);
		t_detail = new JTextArea();
		bt_file = new JButton("파일찾기");
		bt_regist = new JButton("서버전송");
		chooser = new JFileChooser("C:/js_workspace/images");
		
		t_detail.setPreferredSize(new Dimension(270, 160));
		
		setLayout(new FlowLayout());
		add(t_category_idx);
		add(t_product_name);
		add(t_brand);
		add(t_price);
		add(t_discount);
		add(t_detail);
		add(bt_file);
		add(bt_regist);
		
		setSize(300, 400);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		bt_file.addActionListener((c)->{ // c : component
			selectFile();
		});
		bt_regist.addActionListener((c)->{
			
			int result = JOptionPane.showConfirmDialog(RegistForm.this, "서버로 전송하시겠어요,,,");
			if(result!=JOptionPane.OK_OPTION) {
				return;
			}
			// 네트워크 통신은 별도의 쓰레드로 처리하는게 안정적이다(안드로이드에서는 메인 쓰레드 사용 금지)
			// 버튼 누를 때마다 쓰레드 생성
			Thread thread = new Thread() {
				public void run() {
					try {
						regist();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
			thread.start();
		});
	}
	
	public void selectFile() {
		chooser.showOpenDialog(this);
		
		// 유저가 선택한 파일 얻기
		file = chooser.getSelectedFile();
		
	}
	
	// text + binary file도 함께 Http로 전송하므로 multipart/form-data로 전송해야 한다
	//  text + binary file = multipart/form-data
	public void regist() throws MalformedURLException, IOException{
		URL url = new URL(host);
		con = (HttpURLConnection)url.openConnection(); // 웹용으로 전환

		// 웹 전송을 위한 코드 작성 (머리와 몸 구성, HttpURLConnection 필요)
		// 머리(Header) 구성하기
		con.setRequestProperty("Content-Type", "multipart/form-data;charset=UTF-8;boundary="+boundary); // 데이터와 데이터의 경계를 정해야 하는데 그 경계는 ,,,
		con.setRequestMethod("POST");
		con.setDoOutput(true); // 내보낼고양 서버에 보낼 때
		con.setDoInput(true); // 받을고양 서버에서 가져올 때
		con.setUseCaches(false); // 캐시 없애기
		con.setConnectTimeout(2500); // 응용 프로그램이 서버에 말 걸었는데 반응이 없으면 포기
		
		// 몸(Body) 구성하기(스트림으로 처리)
		DataOutputStream ds = new DataOutputStream(con.getOutputStream());
		
		// 텍스트 파라미터의 시작을 알리는 구분자 선언
		// ds.writeBytes("--"+boundary+"\r\n");
		ds.writeBytes(hypen+boundary+line); // 시작할 때
		// 바디를 구성하는 요소들 간에는 줄바꿈으로 구분한다
		// ds.writeBytes("Content-Disposition:form-data;파라미터명"); // 파라미터 선언 뒤에는 줄바꿈 표시 필수
		ds.writeBytes("Content-Disposition:form-data;name=\"category.category_idx\""+line); // 파라미터 선언 뒤에는 줄바꿈 표시 필수
		ds.writeBytes("Content-Type:text/plaint;charset=UTF-8"+line);
		ds.writeBytes(line); // 값 지정 직후에는 라인으로 또 구분
		ds.writeBytes(t_category_idx.getText()+line);
		// ----------------------------------------------------여기까지가 파라미터 하나에 대한 블럭
		
		ds.writeBytes(hypen+boundary+line); // 시작할 때
		ds.writeBytes("Content-Disposition:form-data;name=\"product_name\""+line);
		ds.writeBytes("Content-Type:text/plaint;charset=UTF-8"+line);
		ds.writeBytes(line); // 값 지정 직후에는 라인으로 또 구분
		ds.writeBytes(t_product_name.getText()+line);
		
		ds.writeBytes(hypen+boundary+line); // 시작할 때
		ds.writeBytes("Content-Disposition:form-data;name=\"price\""+line);
		ds.writeBytes("Content-Type:text/plaint;charset=UTF-8"+line);
		ds.writeBytes(line); // 값 지정 직후에는 라인으로 또 구분
		ds.writeBytes(t_price.getText()+line);
		
		ds.writeBytes(hypen+boundary+line); // 시작할 때
		ds.writeBytes("Content-Disposition:form-data;name=\"brand\""+line);
		ds.writeBytes("Content-Type:text/plaint;charset=UTF-8"+line);
		ds.writeBytes(line); // 값 지정 직후에는 라인으로 또 구분
		ds.writeBytes(t_brand.getText()+line);
		
		ds.writeBytes(hypen+boundary+line); // 시작할 때
		ds.writeBytes("Content-Disposition:form-data;name=\"detail\""+line);
		ds.writeBytes("Content-Type:text/plaint;charset=UTF-8"+line);
		ds.writeBytes(line); // 값 지정 직후에는 라인으로 또 구분
		ds.writeBytes(t_detail.getText()+line);
		
		// 파일 파라미터 처리
		ds.writeBytes(hypen+boundary+line); // 시작할 때 
		// ds.writeBytes("Content-Disposition:form-data;name=\"detail\";filename=\"파일명\""+line);
		ds.writeBytes("Content-Disposition:form-data;name=\"photo\";filename=\""+file.getName()+"\""+line);
		ds.writeBytes("Content-Type:image/jpeg"+line); // 파일의 종류, 형식, jpeg는 변수로 처리하든지 ,,,
		ds.writeBytes(line); // 값 지정 직후에는 라인으로 또 구분
		
		// 파일 쪼개서 전송
		FileInputStream fis = new FileInputStream(file);
		byte[] buff = new byte[1024];
		
		int data = -1;
		
		while(true) {
			data = fis.read(buff);
			if(data==-1) break;
			ds.write(buff);
		}
		
		// 전송
		ds.writeBytes(line);
		ds.writeBytes(hypen+boundary+hypen+line); // 끝 맺을 때
		ds.flush(); // 버퍼 처리된 출력스트림의 경우 flush()가 사용됨
		fis.close();
		ds.close();
		
		// 웹서버로부터 받은 http 상태 코드로 성공여부를 따져보자
		int status = con.getResponseCode();
		if(status==HttpURLConnection.HTTP_OK) {
			System.out.println("성공");
		} else {
			System.out.println("실패");
		}
	}
	
	public static void main(String[] args) {
		new RegistForm();
	}
}
