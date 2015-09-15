import java.io.*;
import java.util.ArrayList;

public class Str140917 {

	public static boolean isNumber(String val) {
	    try {
	        Integer.parseInt(val);
	        return true;
	    } catch (NumberFormatException nfex) {
	        return false;
	    }
	}
	
	public static boolean isFloat(String val) {
	    try {
	        Float.parseFloat(val);
	        return true;
	    } catch (NumberFormatException nfex) {
	        return false;
	    }
	}
	
	//指定した目的地かどうか
	public static boolean isCheck(String val, ArrayList<Integer> array) {
		int check = 0;

	    try {
	        check = Integer.parseInt(val);
	    } catch (NumberFormatException nfex) {
	    }
	    
	    //線形探索
	    for(int c : array)
	    	if(check == c) return true;

		return false;
	}
	
	public static boolean isItem(String Item, ArrayList<String> array) {
	    
	    //線形探索
	    for(String s : array)
	    	if(Item.equals(s)) return true;

		return false;
	}
	
	public static void Load_Write(BufferedReader br, BufferedWriter[] bw , int exist, int exist2, ArrayList<Integer> array, ArrayList<String> array2, int f, int f2) {
		try {
	        String line;
	        String[] strAry;
	        
	        String tmp = null;
	        float tmpf = 0.0f;
	        int tmpi = 0, count = 0;
	        int i = 0, j = 0, k = 0, flag = 0, flag2 = 0, flag3 = 0;
	        
	        while ((line = br.readLine()) != null) {
	            System.out.println(line);
	            strAry = line.split(" ");
	            for(String s : strAry){
	            	//System.out.println(s+isNumber(s));
	            	if(i < 2){
	        			bw[0].write(s + " ");
	        			//System.out.println(s);
	        			//あて先と途中の結果
	        			//項目に関してiは++されない
	            		if(isNumber(s)){
	            			i++;
	            			//指定した目的地か
	                  		if(i == 1 && exist==1 && isCheck(s, array))
	                  			flag2 = 1;
	            		}
	            		else if(isFloat(s))
	            			i++;
	            		
	            		else if(s.equals("-"))
	            			i++;
	            		
	            		else k++;
	            		
	            		if(i == 1){
	            			j++;                			
	            			
	            			//項目による分類
	            			if(j == 2){
	            				//一番最初の項目
	            				if(tmp == null)
	            					tmp = new String(s);
	            				//2番目以降
	            				else{
	            					//違う項目が来たらファイル書き込み
	            					if(!tmp.equals(s)){
	            						//平均(int)スループット
	            						if(flag == 1)
	            							bw[1].write(tmp + " " + (float)tmpi/count);
	            						//平均(float)遅延
	            						else
	            							bw[1].write(tmp + " " + tmpf/count);
	            		                bw[1].newLine();
	            		                bw[1].newLine();
	            		                
	            						tmp = new String(s);
	            						
	            						count = 0;
	            						tmpi = 0;
	            						tmpf = 0.0f;
	            						//flag=0;
	            					}
	            					flag = 0;
	            				}
	            			}
	            		}
	            		
	            		//if(!tmp.equals(s))に引っかからない，つまり同じ項目の間は足し続ける
	            		else{
	            			//sumスループット
	            			if(isNumber(s)){
	            				tmpi += Integer.parseInt(s);
	            				//System.out.println(tmpi);
	            				count++;
	            				flag = 1;
	            			}      
	            			//sum遅延
	            			else if(isFloat(s)){
	            				tmpf += Float.parseFloat(s);
	            				//System.out.println(tmpf);
	            				count++;
	            				flag = 2;
	            			} 
	            		}
	            		
	            		if(k > 0){
	            			if(exist2 == 1 && isItem(s, array2))
	            				flag3 = 1;
	            		}
	            		//System.out.println(flag3);
	        		}
	            }

	            //System.out.println(exist == 0 && exist2 == 1 && flag3 == 1);
	            //指定した項目の行のみ出力
	  			if(f2 == 1 && flag3 == 1){
					bw[5].write(line);
					bw[5].newLine();
	  			}
	  			
	  			if(exist == 1 && flag2 == 1){
					bw[2].write(line);
					bw[2].newLine();

		  			if(exist2 == 1 && flag3 == 1){
						bw[3].write(line);
						bw[3].newLine();
						bw[4].write(line);
						bw[4].newLine();
					}
				}

	  			else{
	  				//fが0になるのはcount == 1 && exist2 == 1
		  			if(f != 0 && exist2 == 1 && flag3 == 1){
						bw[3].write(line);
						bw[3].newLine();
						bw[4].write(line);
						bw[4].newLine();
					}
		  			else if(exist == 0 && exist2 == 1 && flag3 == 1){
						bw[3].write(line);
						bw[3].newLine();
						bw[4].write(line);
						bw[4].newLine();
		  			}
	  			}
	            flag2 = 0;
	            flag3 = 0;
	            i = 0;
	            j = 0;
	            k = 0;
	            //for(int i = 0; i < 6; i++)
	            //	bw.write(strAry[i] + " ");
	            bw[0].newLine();
	
	            //break;
	        }
	        //最後のを出力
	        if(flag == 1)
				bw[1].write(tmp + " " + (float)tmpi/count);
			else
				bw[1].write(tmp + " " + tmpf/count);
	        bw[1].newLine();
	        
		} catch (IOException e) {
            System.out.println(e);
        }
	}
	
	//overload 
	//Numbers.txt is not exist && Item.txt is exist
	public static void Load_Write() {
		try {
	        FileReader in = new FileReader("ELOther.txt");
	        FileWriter[] out = new FileWriter[2];
            out[0] = new FileWriter("EL.txt"); //中間値を削ったもの
            out[1] = new FileWriter("AVE.txt"); //平均を出したもの
	        BufferedReader br = new BufferedReader(in);
	        BufferedWriter[] bw = new BufferedWriter[4];
            bw[0] = new BufferedWriter(out[0]);
            bw[1] = new BufferedWriter(out[1]);
	        
	        String line;
	        String[] strAry;
	        
	        String tmp = null;
	        float tmpf = 0.0f;
	        int tmpi = 0, count = 0;
	        int i = 0, j = 0, flag = 0;
	        
	        while ((line = br.readLine()) != null) {
	            System.out.println(line);
	            strAry = line.split(" ");
	            for(String s : strAry){
	            	//System.out.println(s+isNumber(s));
	            	if(i < 2){
	        			bw[0].write(s + " ");
	        			//System.out.println(s);
	        			//あて先と途中の結果
	        			//項目に関してiは++されない
	            		if(isNumber(s))
	            			i++;
	            		else if(isFloat(s))
	            			i++;
	            		
	            		if(i == 1){
	            			j++;                			
	            			
	            			//項目による分類
	            			if(j == 2){
	            				//一番最初の項目
	            				if(tmp == null)
	            					tmp = new String(s);
	            				//2番目以降
	            				else{
	            					//違う項目が来たらファイル書き込み
	            					if(!tmp.equals(s)){
	            						//平均(int)スループット
	            						if(flag == 1)
	            							bw[1].write(tmp + " " + (float)tmpi/count);
	            						//平均(float)遅延
	            						else
	            							bw[1].write(tmp + " " + tmpf/count);
	            		                bw[1].newLine();
	            		                bw[1].newLine();
	            		                
	            						tmp = new String(s);
	            						
	            						count = 0;
	            						tmpi = 0;
	            						tmpf = 0.0f;
	            					}
	            					flag = 0;
	            				}
	            			}
	            		}
	            		
	            		//if(!tmp.equals(s))に引っかからない，つまり同じ項目の間は足し続ける
	            		else{
	            			//sumスループット
	            			if(isNumber(s)){
	            				tmpi += Integer.parseInt(s);
	            				//System.out.println(tmpi);
	            				count++;
	            				flag = 1;
	            			}      
	            			//sum遅延
	            			else if(isFloat(s)){
	            				tmpf += Float.parseFloat(s);
	            				//System.out.println(tmpf);
	            				count++;
	            				flag = 2;
	            			} 
	            		}
	            		
	            		//System.out.println(s + k);
	        		}
	            }
	  			
	            i = 0;
	            j = 0;
	            bw[0].newLine();
	
	        }
	        //最後のを出力
	        if(flag == 1)
				bw[1].write(tmp + " " + (float)tmpi/count);
			else
				bw[1].write(tmp + " " + tmpf/count);
	        bw[1].newLine();
	        
            bw[0].flush();
            bw[1].flush();
            br.close();
            in.close();
            out[0].close();
            out[1].close();
	        
		} catch (IOException e) {
            System.out.println(e);
        }
		
	}
		
	static int count = 0;
	static int mode2 = 1;
	public static int m(String[] args, int mode) {
        count++;
		int exist = 1;
        int exist2 = 1;
        int flag = 0;
        int flag2 = 0;
        
        FileReader[] in = new FileReader[3];
        FileWriter[] out = new FileWriter[6];
        BufferedReader [] br = new BufferedReader[3];
        BufferedWriter[] bw = new BufferedWriter[6];
        
        String line;
        
        exist = mode;

		try {
			if(exist==1)
				in[0] = new FileReader(args[0]);
			else
				in[0] = new FileReader("ELOther.txt");
			
            in[1] = null;
            br[1] = null;

            //Numbers.txtがないとELOther.txtは生成されない
            try {
            	in[1] = new FileReader("Numbers.txt");
            	br[1] = new BufferedReader(in[1]);
            }catch(FileNotFoundException e){
            	exist = 0;
            }
            
            //Items.txtがあるか
            try {
            	in[2] = new FileReader("Items.txt");
            	br[2] = new BufferedReader(in[2]);
            }catch(FileNotFoundException e){
            	exist2 = 0;
            	mode2 = exist2;
            }
            
            out[0] = new FileWriter("EL.txt"); //中間値を削ったもの
            out[1] = new FileWriter("AVE.txt"); //平均を出したもの
            out[2] = null;
            bw[2] = null;
            
            if(exist == 1){
            	out[2] = new FileWriter("ELOther.txt"); //指定した目的地以外を削ったもの
            	bw[2] = new BufferedWriter(out[2]);
            }

            if(count == 1 && exist2 == 1){
            	out[3] = new FileWriter("ELOther2.txt");
            	bw[3] = new BufferedWriter(out[3]);
            	out[4] = new FileWriter("ELOther_dest_item.txt");
            	bw[4] = new BufferedWriter(out[4]);
            	flag = 0;
            }
            
            out[5] = null;
            bw[5] = null;

            if(count == 1 && exist == 1 && mode2 == 1){
            	out[5] = new FileWriter("ELOther_item.txt");
            	bw[5] = new BufferedWriter(out[5]);
            	flag2 = 1;
            }

            //動かない（exeでは生成されない）
            //FileWriter out = new FileWriter("EL_" + strFile[0] + ".txt");
            //FileWriter out2 = new FileWriter("AVE_" + strFile[0] + ".txt");
            br[0] = new BufferedReader(in[0]);
            bw[0] = new BufferedWriter(out[0]);
            bw[1] = new BufferedWriter(out[1]);
                        
            // 目的地を振り分けるための配列
            ArrayList<Integer> array = null;
            if(exist == 1){
            	array = new ArrayList<Integer>();
            	
	            while ((line = br[1].readLine()) != null)
	            	array.add(Integer.parseInt(line));
	            
	            if(count==1){
	            	System.out.println("in ./Numbers.txt");
	            	for(int c : array)
	            		System.out.println(c);
	            	System.out.println();
	            }
            }
            
            // 項目を振り分けるための配列
            ArrayList<String> array2 = null;
            if(exist2 == 1){
            	array2 = new ArrayList<String>();
            	
	            while ((line = br[2].readLine()) != null)
	            	array2.add(line);
	            
	            if(count==1){
	            	System.out.println("in ./Items.txt");
	            	for(String c : array2)
	            		System.out.println(c);
	            }
            }
                        
            Load_Write(br[0], bw, exist, mode2, array, array2, flag, flag2);
            
            bw[0].flush();
            bw[1].flush();
            br[0].close();
            in[0].close();
            out[0].close();
            out[1].close();
            
            if(exist == 1){
                br[1].close();
                in[1].close();
            	bw[2].flush();
            	out[2].close();
            }
            
            if(count == 1 && exist2 == 1){
                br[2].close();
                in[2].close();
            	bw[3].flush();
            	out[3].close();
            	bw[4].flush();
            	out[4].close();
            }
            
            if(flag2 == 1){
            	bw[5].flush();
            	out[5].close();
            }
            	
        } catch (IOException e) {
            System.out.println(e);
        }
		
		File fileA = null;
		File fileB = null;
		File fileC = null;
		
		if(count == 1 && exist == 1 && mode2 == 1){
			fileA = new File("ELOther.txt");
			fileB = new File("ELOther_dest.txt");
			fileC = new File("ELOther2.txt");
			
		    fileA.renameTo(fileB);
			if (fileA.exists())
				fileA.delete();
			fileC.renameTo(fileA);
		}
		
		else{
			fileC = new File("ELOther2.txt");
			fileA = new File("ELOther.txt");
			
			if(exist == 0 && mode2 > 0){
				if (fileA.exists())
					fileA.delete();
			}
			fileC.renameTo(fileA);
		}
		
		return exist;
	}
	
	public static void main(String[] args){
		int exist;
		
		File fileA = new File("ELOther.txt");
		File fileB = new File("ELOther_dest.txt");
		File fileC = new File("ELOther_item.txt");
		File fileD = new File("ELOther_dest_item.txt");
		File fileE = new File("AVE.txt");
		File fileF = new File("EL.txt");
		
		if (fileA.exists())
			fileA.delete();
		if (fileB.exists())
			fileB.delete();
		if (fileC.exists())
			fileC.delete();
		if (fileD.exists())
			fileD.delete();
		if (fileE.exists())
			fileE.delete();
		if (fileF.exists())
			fileF.delete();
		
		exist = m(args, 1);

		if(exist == 1){
			m(args, exist + 1);
			
			if(mode2 == 1){
				if (fileA.exists())
						fileA.delete();
			}
			
			else if(mode2 == 0){
				if (fileB.exists())
					fileB.delete();
				fileA.renameTo(fileB);
				if (fileC.exists())
					fileC.delete();
				if (fileD.exists())
					fileD.delete();
			}
		}
		
		else{
			if(mode2 == 1){
				Load_Write();
				if (fileC.exists())
					fileC.delete();
				fileD.renameTo(fileC);
				if (fileA.exists())
					fileA.delete();
				if (fileB.exists())
					fileB.delete();
			}
			
			else{
				if (fileA.exists())
					fileA.delete();
				if (fileB.exists())
					fileB.delete();
				if (fileC.exists())
					fileC.delete();
				if (fileD.exists())
					fileD.delete();
			}
		}
	}
}
