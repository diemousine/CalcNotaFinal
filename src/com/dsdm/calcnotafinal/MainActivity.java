package com.dsdm.calcnotafinal;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Nota do autor
		new AlertDialog.Builder(this).setTitle("NOTA DO AUTOR").setMessage("App para calcular a nota mínima da Prova Final.\n© 2014 Diego Mousine -\ndiemousine@hotmail.com").setNeutralButton("OK", null).show();
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	/**
	 * Função que cálcula a média final necessária para passar.
	 */
	public void calcularNotaFinal(View view) {
		
		// Declara as TextViews
		TextView mediaAtual = (EditText) findViewById(R.id.campo_ma);
		TextView mediaFinal = (EditText) findViewById(R.id.campo_mf);
		TextView multMa = (EditText) findViewById(R.id.mult_ma);
		TextView multPf = (EditText) findViewById(R.id.mult_pf);
		TextView nota = (TextView) findViewById(R.id.aviso_valor_nota);
		
		// Declara as variáveis usada na equação
		double mA = 0;
		double mF = 0;
		double mMA = 0;
		double mPF = 0;
		double nPF = 0;
		
		// Verifica se todos os campos foram preenchidos
		try {
			mA = Double.parseDouble(mediaAtual.getText().toString());
			mF = Double.parseDouble(mediaFinal.getText().toString());
			mMA = Double.parseDouble(multMa.getText().toString());
			mPF = Double.parseDouble(multPf.getText().toString());
		} catch (NumberFormatException e) {
			Toast.makeText(this, "PREENCHA TODOS OS CAMPOS", Toast.LENGTH_SHORT).show();
			return;
		}

		// Verifica se todos os valores foram digitados corretamente
		if(mA < 0 || ((mA*mMA)/(mMA+mPF)) >= mF) { 
			Toast.makeText(this, "ERRO: ALGUM VALOR DIGITADO É INVÁLIDO", Toast.LENGTH_SHORT).show();
			return;
		}
		
		// Calcula o valor mínimo da Prova Final
		nPF = notaProvaFinal(mA, mF, mMA, mPF);

		if(nPF > 10) { Toast.makeText(this, "É SÉRIO ISTO???", Toast.LENGTH_SHORT).show(); }

		// Exibe o resultado na TextView aviso_valor_nota
		nota.setText(String.valueOf(nPF));
	}
	
	protected double notaProvaFinal(double ma, double mf, double mma, double mpf) {
		double valorPF = mf-((ma*mma)/(mma+mpf));
		return (valorPF*(mma+mpf))/mpf;
	}
}
