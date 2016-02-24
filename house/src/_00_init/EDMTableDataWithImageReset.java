package _00_init;
/*  
    程式說明：建立表格與設定初始測試資料。

    表格包括：eMember, eBook, eBookCompany, Orders, OrderItems
      
(1) eMember欄位說明:
    seqNo       : 會員流水號Pri.Key  整數      IDENTITY
    memberId    : 會員編號           字串      20
    name        : 姓名               字串      32
    password    : 密碼               字串      32
    address     : 地址               字串      64
    email       : email              字串      64
    tel         : 電話日             字串      15
    userType    : 會員類別           字串      10
    experience  : 使用經驗           整數   
    register    : 註冊時間           datetime
    totalAmount : 訂購總金額         money
    memberImage : 照片               image
    fileName    : 封面圖檔檔名       字串      20
    
(2) eBook欄位說明:
    bookId      : 書籍代號Pri.Key    整數      IDENTITY
    title       : 書籍名稱           字串      50
    author      : 作者               字串      28
    price       : 價格               money
    discount    : 折扣               money
    companyId   : 出版社代號         整數      
    fileName    : 封面圖檔檔名       字串      20
    bookNo      : 書號               字串      20
    CoverImage  : 封面照片           image   

(3) eBookCompany欄位說明:
    id 		     : PrimaryKey         整數      IDENTITY
    name         : 出版社名稱         字串      60
    address      : 出版社地址         字串      60
    url          : 出版社URL          字串      120
    
(4) Orders欄位說明:
    orderNo        : 訂單編號PrimaryKey 整數      IDENTITY
    userId         : 客戶編號           字串      20
    totalAmount    : 總金額             money
    shippingAddress: 出貨地址           字串      64    
    BNO            : 統一編號           字串       8
    invoiceTitle   : 發票抬頭           字串      72
    orderDate      : 訂單日期           datetime 
    ShippingDate   : 出貨日期           datetime
    CancelTag      : 取消               字串       1
    
(5) OrderItems欄位說明:
    seqNo        : PrimaryKey            整數      IDENTITY
    orderNo      : 訂單編號              整數
    bookID       : 書籍代號              整數
    Description  : 說明                  字串      72
    amount       : 數量                  int
    unitPrice    : 單價                  money
    Discount     : 折扣                  money
 
*/
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
public class EDMTableDataWithImageReset {
   public static final String UTF8_BOM = "\uFEFF";  // 定義 UTF-8的BOM字元 
   
   public static void main(String args[]) {
      Connection con;
      PreparedStatement pstmt;
      PreparedStatement pstmt1;
      Statement stmt;
      String dropString;   
      String createString;
      String line  = "";
      String sql   = "";
      String sql1  = "";
      String encrypedString = "";
      try {
          // 連上後端的資料庫
          con =  DriverManager.getConnection(
        		  GlobalService.DB_URL, 
        		  GlobalService.USERID, 
        		  GlobalService.PASSWORD);
          // 建立Statement物件，以便傳送SQL命令到後端的資料庫
          stmt = con.createStatement();
          
      }  catch(SQLException e) {
          System.err.println("存取資料庫有關的例外：" + e.getMessage() );
          e.printStackTrace() ;
          return ;
      }
      
      
      
      // 定義刪除Users表格的SQL命令
      dropString = "DROP Table Users " ;
      try {    
    	  // 執行刪除Users表格的SQL命令
          stmt.executeUpdate(dropString); 
          // 印出執行成功的訊息
          System.out.println("Users表格刪除成功") ;
      } catch(SQLException e) {
          System.err.println("刪除Users表格時發生例外: " + e.getMessage());
      }
      
      
      
      // 定義新建Users表格的SQL命令      
      createString = "Create Table Users " +
             "(User_id int NOT NULL IDENTITY Primary Key , " +
             " User_account		varchar(20), " +
             " User_name		varchar(32), " +
             " User_password    varchar(32), " +
             " User_address 	varchar(64), " +
             " User_email 		varchar(64), " +
             " User_phone  		varchar(15), " +
             " User_type		varchar(10), " +
             " User_gender      varchar(10), " +
             " User_birth       Date , " +
             " User_photo       image, " +
             " User_photoName   varchar(20) " +
             " ) " ;    
      try {
          //  執行新建Users表格的SQL命令   
          stmt.executeUpdate(createString);
          // 印出執行成功的訊息
          System.out.println("Users表格產生成功") ;
          //  對Users表格新增三筆測試用紀錄  
          sql1 = "insert into Users " +
	  		" (User_account, User_name, User_password, User_address, User_email, User_phone, User_type, " +
	  		" User_gender, User_birth, User_photo, User_photoName) " +
	  		" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
          pstmt1 = con.prepareStatement(sql1);
          //Statement pstmt2 = con.createStatement();
          pstmt1.setString(1, "mouse");
          pstmt1.setString(2, "老鼠");
          encrypedString = GlobalService.encryptString("123");      //連到GlobalService加密密碼~!
          pstmt1.setString(3, GlobalService.getMD5Endocing(encrypedString));  //密碼都是 123
          pstmt1.setString(4, "新北市板橋區中正路100號");
          pstmt1.setString(5, "mouse@gmail.com");
          pstmt1.setString(6, "0987987001");
          pstmt1.setString(7, "Customer");
          pstmt1.setString(8, "男");
          //java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());
          pstmt1.setString(9, "1988/09/05");
          // 讀取要寫入表格的圖片檔
    	  File imageFile = new File("src/images/smallPic/mouselion.jpg");
    	  long size = imageFile.length();
    	  InputStream is = new FileInputStream(imageFile);
    	  // 設定Image欄位
    	  pstmt1.setBlob(10, is, size);
    	  // 設定fileName欄位
    	  String fileName = imageFile.getName();
    	  // 調整檔名的字元數
    	  fileName = GlobalService.adjustFileName(fileName, GlobalService.IMAGE_FILENAME_LENGTH);
    	  pstmt1.setString(11, fileName);    	  
          pstmt1.executeUpdate();
          pstmt1.setString(1, "beedog");
          pstmt1.setString(2, "蜜蜂狗");
          encrypedString = GlobalService.encryptString("123");
          pstmt1.setString(3, GlobalService.getMD5Endocing(encrypedString));  //密碼都是 123
          pstmt1.setString(4, "台北市大安區復興南路1段280號2樓");
          pstmt1.setString(5, "beedog@gmail.com");
          pstmt1.setString(6, "0985999002");
          pstmt1.setString(7, "Customer");
          pstmt1.setString(8, "女");
          //now = new java.sql.Timestamp(System.currentTimeMillis());
          pstmt1.setString(9, "1955/04/25");
          // 讀取圖片檔
    	  imageFile = new File("src/images/smallPic/beedog.jpg");
    	  size = imageFile.length();
    	  is = new FileInputStream(imageFile);
    	  // 設定Image欄位
    	  pstmt1.setBlob(10, is, size);    	  
    	  // 設定fileName欄位
    	  fileName = imageFile.getName();
    	  // 調整檔名的字元數
    	  fileName = GlobalService.adjustFileName(fileName, GlobalService.IMAGE_FILENAME_LENGTH);
    	  pstmt1.setString(11, fileName);    	  
    	  pstmt1.executeUpdate();
          // ------------------
          pstmt1.setString(1, "fish");
          pstmt1.setString(2, "魚狗狗");
          encrypedString = GlobalService.encryptString("123");
          pstmt1.setString(3, GlobalService.getMD5Endocing(encrypedString));  //密碼都是 123
          pstmt1.setString(4, "台南市中西區中正路57號2樓");
          pstmt1.setString(5, "fishdog@yahoo.com");
          pstmt1.setString(6, "06-7028925");
          pstmt1.setString(7, "Customer");
          pstmt1.setString(8, "女");
          //now = new java.sql.Timestamp(System.currentTimeMillis());
          pstmt1.setString(9,"1998/04/25");
          
          // 讀取圖片檔
    	  imageFile = new File("src/images/smallPic/fish.jpg");
    	  size = imageFile.length();
    	  is = new FileInputStream(imageFile);
    	  // 設定Image欄位
    	  pstmt1.setBlob(10, is, size);
    	  // 設定fileName欄位
    	  fileName = imageFile.getName();
    	  // 調整檔名的字元數
    	  fileName = GlobalService.adjustFileName(fileName, GlobalService.IMAGE_FILENAME_LENGTH);
    	  
    	  pstmt1.setString(11, fileName);    	  
    	  pstmt1.executeUpdate();
          //  印出資料新增成功的訊息
          System.out.println("Users 資料新增成功") ;
      } catch(SQLException e) {
          System.err.println("新建Users表格時發生例外: " + e.getMessage());
          e.printStackTrace();
      } catch(IOException e) {
          System.err.println("新建Users表格時發生IO例外: " + e.getMessage());
          
         
      } 
      
      
      
      
      
      
//      
//      
//      
//      
//      // 定義刪除eBook表格的SQL命令
//      dropString = "DROP Table eBook " ;
//      try {    
//    	  // 執行刪除eBook表格的SQL命令
//          stmt.executeUpdate(dropString); 
//          // 印出執行成功的訊息
//          System.out.println("eBook表格刪除成功") ;
//      } catch(SQLException e) {
//          System.err.println("刪除eBook表格時發生例外: " + e.getMessage());
//      } 
//      // 定義新建eBook表格的SQL命令      
//      createString = "Create Table eBook " +
//             "(bookID int NOT NULL IDENTITY Primary Key , " +
//             " title		varchar(50), " +
//             " author    	varchar(28), " +
//             " price		money, " +
//             " discount		money, " +             
//             " companyID	int, " +
//             " fileName		varchar(20), " +
//             " bookNo 		varchar(20), " +
//             " CoverImage   image " +
//             " ) " ;    
//      try {
//          // 執行新建eBook表格的SQL命令   
//          stmt.executeUpdate(createString);
//          // 印出執行成功的訊息
//          System.out.println("eBook表格產生成功") ;
//         // 定義新增書籍記錄的SQL命令
//          sql = "insert into eBook " +
//	  		" (title,  author,  price, discount, companyID, fileName, bookNo, CoverImage) " +
//	  		" values (?, ?, ?, ?, ?, ?, ?, ?)";
//          // 建立新增書籍記錄的PreparedStatement物件
//          pstmt = con.prepareStatement(sql);
//          // 讀取eBook表格的初始資料，準備新增到eBook表格內          
//          BufferedReader br = new BufferedReader(new FileReader("WebContent/data/book.dat"));          
//          while ( (line = br.readLine() ) != null){
//        	  // 去除 UTF8_BOM
//        	  if (line.startsWith(UTF8_BOM )){
//        		  line = line.substring(1);
//        	  }
//        	  String[] token = line.split("\\|");
//        	  pstmt.setString(1, token[0]);
//        	  pstmt.setString(2, token[1]);
//        	  pstmt.setString(3, token[2].trim());
//        	  pstmt.setString(4, token[3].trim());
//        	  pstmt.setString(5, token[4].trim());
//        	  pstmt.setString(6, token[5].trim());
//        	  pstmt.setString(7, token[6].trim());
//        	  // 讀取圖片檔
//        	  File aFile = new File("WebContent/images/smallPic/" + token[5].trim());
//        	  long size = aFile.length();
//        	  InputStream is = new FileInputStream(aFile);
//        	  // 設定Image欄位
//        	  pstmt.setBlob(8, is, size);
//              //  執行新增eBook表格之紀錄的SQL命令
//              int r = pstmt.executeUpdate();
//              System.out.println("新增一筆eBook紀錄是否成功=" + r);
//          }
//          //  印出資料新增成功的訊息
//          System.out.println("eBook資料新增成功") ;
//      } catch(SQLException e) {
//          System.err.println("新建eBook表格時發生SQL例外: " + e.getMessage());
//          e.printStackTrace();
//      } catch(IOException e) {
//          System.err.println("新建eBook表格時發生IO例外: " + e.getMessage());
//      } 
//      
//      // 定義刪除eBookCompany表格的SQL命令
//      dropString = "DROP Table eBookCompany " ;
//      try {    
//    	  // 執行刪除eBookCompany表格的SQL命令
//          stmt.executeUpdate(dropString); 
//          // 印出執行成功的訊息
//          System.out.println("eBookCompany表格刪除成功") ;
//      } catch(SQLException e) {
//          System.err.println("刪除eBookCompany表格時發生SQL例外: " + e.getMessage());
//      }
//      // 定義新建eBookCompany表格的SQL命令      
//      createString = "Create Table eBookCompany " +
//             "(id  int NOT NULL IDENTITY Primary Key , " +
//             " name		    varchar(60), " +
//             " address    	varchar(60), " +
//             " url  		varchar(120) " +
//             " ) " ;    
//      try {
//          //  執行新建eBookCompany表格的SQL命令   
//          stmt.executeUpdate(createString);
//          // 印出表格產生成功的訊息
//          System.out.println("eBookCompany表格產生成功") ;
//          // 讀取eBookCompany表格的初始資料，準備新增到eBook表格內
//          BufferedReader br = new BufferedReader(new FileReader("WebContent/data/bookCompany.dat"));
//          while ( (line = br.readLine() ) != null){
//        	  if (line.startsWith(UTF8_BOM )){
//        		  line = line.substring(1);
//        	  }
//        	  String[] token = line.split("\\|");
//        	  sql = "insert into eBookCompany (name,  address,  url ) " + 
//        	  "values ('" + token[0] + "','" + token[1] + "','" + token[2] + "')";
//              //  執行新增eBookCompany表格之紀錄的SQL命令
//              stmt.executeUpdate(sql);
//          }
//          //  印出資料新增成功的訊息
//          System.out.println("eBookCompany 資料新增成功") ;
//      } catch(SQLException e) {
//          System.err.println("新建eBookCompany表格時發生SQL例外: " + e.getMessage());
//      } catch(IOException e) {
//          System.err.println("新建eBookCompany表格時發生IO例外: " + e.getMessage());
//      } 
//      
      
      
      
      
      
//      // ********************
//      // 定義刪除Orders表格的SQL命令
//      dropString = "DROP Table Orders" ;
//      try {    
//    	  // 執行刪除Orders表格的SQL命令
//          stmt.executeUpdate(dropString); 
//          // 印出執行成功的訊息
//          System.out.println("Orders表格刪除成功") ;
//      } catch(SQLException e) {
//          System.err.println("刪除Orders表格時發生SQL例外: " + e.getMessage());
//      }
//      // 定義新建Orders表格的SQL命令      
//      createString = "Create Table Orders " +
//             "(orderNo  int NOT NULL IDENTITY Primary Key , " +
//             " userId            varchar(20), " +
//             " totalAmount       money, " +
//             " shippingAddress   varchar(64), " +
//             " BNO               varchar(8), " +
//             " invoiceTitle      varchar(72), " +
//             " orderDate         DateTime, " +
//             " ShippingDate      DateTime, " +
//             " CancelTag         Char(1) "  + 
//             " ) " ;    
//      try {
//          //  執行新建Orders表格的SQL命令
//          stmt.executeUpdate(createString);
//          // 印出執行成功的訊息
//          System.out.println("Orders表格產生成功") ;
//      } catch(SQLException e) {
//          System.err.println("新建Orders表格時發生SQL例外: " + e.getMessage());
//      } 
//      
//      // 定義刪除OrderItems表格的SQL命令
//      dropString = "DROP Table OrderItems" ;
//      try {    
//    	  // 執行刪除OrderItems表格的SQL命令
//          stmt.executeUpdate(dropString); 
//          // 印出執行成功的訊息
//          System.out.println("OrderItems表格刪除成功") ;
//      } catch(SQLException e) {
//          System.err.println("刪除OrderItems表格時發生SQL例外: " + e.getMessage());
//      }
//      // 定義新建OrderItems表格的SQL命令      
//      createString = "Create Table OrderItems " +
//             "(seqno            int NOT NULL IDENTITY Primary Key , " +
//             " orderNo          int, " +
//             " bookID           int, " +
//             " Description      varchar(72), " +
//             " amount           int, " +
//             " unitPrice        Money, " +
//             " Discount         Money " +             
//             " ) " ;    
//      try {
//          //  執行新建OrderItems表格的SQL命令   
//          stmt.executeUpdate(createString);
//          // 印出執行成功的訊息
//          System.out.println("OrderItems表格產生成功") ;
//      } catch(SQLException e) {
//          System.err.println("新建OrderItems表格時發生SQL例外: " + e.getMessage());
//      }  
      
      
      
      
      
      // 關閉相關的物件
      finally {
         try {
             if (stmt!=null)  stmt.close();
         } catch(Exception e) {
             System.err.println("關閉相關物件時發生例外: " + e);
         }
         try {
             if (stmt!=null)  con.close();
         } catch(Exception e) {
             System.err.println("關閉相關物件時發生例外: " + e);
         }
      }
   }
}