package entity;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.Toolkit;
import java.util.regex.Pattern;

 

public class MQDocument extends PlainDocument {

 private static final long serialVersionUID = -4462693078138709956L;

 private String limit = null; // �����ַ����Ƶ�������ʽ

 private int maxLength = -1; // �����ַ���󳤶ȵ����ƣ�-1Ϊ������

 private double maxValue = 0; // �������������֣������ֵ����

 private boolean isMaxValue = false; // �Ƿ���������ֵ����

 private Toolkit toolkit = null; // �����ڴ����ʱ�򷢳�ϵͳ����

 private boolean beep = false; // �Ƿ�����trueΪ��������
 
 private int number_of_dot = 0; //��¼�����С�������

 // ���췽��

 public MQDocument() {
  super();
  this.init();
 }

 public MQDocument(Content c) {
  super(c);
  this.init();
 }

 
 private void init() {
  toolkit = Toolkit.getDefaultToolkit();
 }

 // ���췽������

 
 public void setCharLimit(String limit) {
  this.limit = limit;
 }

 
 public String getCharLimit() {
  return this.limit;
 }

 
 public void clearLimit() {
  this.limit = null;
 }

 
 public boolean isOfLimit(CharSequence input) {
  if (limit == null) {
   return true;
  } else {
   return Pattern.compile(limit).matcher(input).find();
  }
 }

 
 public boolean isEmptyLimit() {
  if (limit == null) {
   return true;
  } else {
   return false;
  }
 }

 
 public void setMaxLength(int maxLength) {
  this.maxLength = maxLength;
 }

 
 public void cancelMaxLength() {
  this.maxLength = -1;
 }

 
 public void setMaxValue(double maxValue) {
  this.isMaxValue = true;
  this.maxValue = maxValue;
 }

 
 public boolean isMaxValue() {
  return this.isMaxValue;
 }

 
 public double getMaxValue() {
  return this.maxValue;
 }

 
 public void cancelMaxValue() {
  this.isMaxValue = false;
  this.maxValue = 0;
 }

 
 public void reset() {
  clearLimit();
  cancelMaxLength();
  cancelMaxValue();
 }

 
 public void errorBeep(boolean beep) {
  this.beep = beep;
 }

 
 public boolean isErrorBeep() {
  return beep;
 }

 public void insertString(int offs, String str, AttributeSet a)
   throws BadLocationException, NumberFormatException {
  // ���ַ���Ϊ�գ�ֱ�ӷ��ء�
  if (str == null) {
   return;
  }
  boolean b = true;
	char[] ch = str.toCharArray();
	for (int i = 0; i < ch.length; i++) {
		String temp = String.valueOf(ch[i]);
//		if(temp.equals('.')) {
////			System.out.print("******");
//			number_of_dot++;
//			if(number_of_dot > 1) {
//				b = false;
//			}
//		}
		// ���Ҫ������ַ���������Χ��
		if (!isOfLimit(temp)) {
			b = false;
		}
   // ������ַ��������ƣ��������ڵ��ַ������Ѿ����ڻ��������
   if (maxLength > -1 && this.getLength() >= maxLength) {
    b = false;
   }

  }
  // ��������������������
  if (isMaxValue) {
   String s = this.getText(0, this.getLength()); // �ĵ������е��ַ�
   s = s.substring(0, offs) + str + s.substring(offs, s.length());
   if (Double.parseDouble(s) > maxValue) {
    if (beep) {
     toolkit.beep(); // ��������
    }
    return;
   }
  }

  // ������벻�Ϸ�
  if (!b) {
   if (beep) {
    toolkit.beep(); // ��������
   }
   return;
  }

  super.insertString(offs, new String(ch), a);
 }

}