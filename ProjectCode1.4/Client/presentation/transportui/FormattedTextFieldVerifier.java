package transportui;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;

class FormattedTextFieldVerifier extends InputVerifier{
	   public boolean verify(JComponent component){
	      JFormattedTextField field = (JFormattedTextField) component;
	      //���û���������ϸ�ʽ���򷵻�true�����򷵻�false
	      return field.isEditValid();
	   }
	}




