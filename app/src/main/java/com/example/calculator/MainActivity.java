package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView resultView;
    StringBuffer phepToan = new StringBuffer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultView = findViewById(R.id.result_view);
        findViewById(R.id.zero_btn).setOnClickListener(this);
        findViewById(R.id.one_btn).setOnClickListener(this);
        findViewById(R.id.two_btn).setOnClickListener(this);
        findViewById(R.id.three_btn).setOnClickListener(this);
        findViewById(R.id.four_btn).setOnClickListener(this);
        findViewById(R.id.five_btn).setOnClickListener(this);
        findViewById(R.id.six_btn).setOnClickListener(this);
        findViewById(R.id.seven_btn).setOnClickListener(this);
        findViewById(R.id.eight_btn).setOnClickListener(this);
        findViewById(R.id.nine_btn).setOnClickListener(this);
        findViewById(R.id.add_btn).setOnClickListener(this);
        findViewById(R.id.sub_btn).setOnClickListener(this);
        findViewById(R.id.devide_btn).setOnClickListener(this);
        findViewById(R.id.mul_btn).setOnClickListener(this);
        findViewById(R.id.result_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.CE_btn:{
                for (int i = phepToan.length()-1; i >= 0; i--) {
                    if (laToanTu(phepToan.charAt(i))) {
                        break;
                    } else {
                        phepToan.deleteCharAt(i);
                    }
                }
                resultView.setText(phepToan);

            }; break;
            case R.id.C_btn: {
                phepToan = new StringBuffer();
                resultView.setText(phepToan);
            }; break;
            case R.id.BS_btn: {
                if (!phepToan.toString().equals("")) {
                    phepToan.deleteCharAt(phepToan.length()-1);
                    resultView.setText(phepToan);
                }
            }; break;
            case R.id.nag_btn:{

            };break;
            case R.id.add_btn:
            case R.id.sub_btn:
            case R.id.mul_btn:
            case R.id.devide_btn: {
                if (!phepToan.toString().equals("")) {
                    if ( !laToanTu(phepToan.charAt(phepToan.length()-1))) {
                        phepToan.append(toanTu(id));
                    } else {
                        phepToan.setCharAt(phepToan.length()-1, toanTu(id));
                    }
                    resultView.setText(phepToan);
                }
            }; break;
            case R.id.result_btn: {
                if (kiemTraDinhDang(phepToan)){
                    int result = tinhToan(phepToan);
                    resultView.setText("" + result);
                    phepToan = new StringBuffer(""+result);

                } else {
                    Toast toast = Toast.makeText(this,"Không đúng định dạng", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }; break;
            default: {
                int number = numberBtn(view.getId());
                if (number != -1) {
                    phepToan.append(numberBtn(view.getId()));
                    resultView.setText(phepToan);
                }
            }
        }
    }

    public int numberBtn(int id) {
        switch (id) {
            case R.id.zero_btn: return 0;
            case R.id.one_btn: return 1;
            case R.id.two_btn: return 2;
            case R.id.three_btn: return 3;
            case R.id.four_btn: return 4;
            case R.id.five_btn: return 5;
            case R.id.six_btn: return 6;
            case R.id.seven_btn: return 7;
            case R.id.eight_btn: return 8;
            case R.id.nine_btn: return 9;
            default: return -1;
        }
    }

    public int tinhToan(StringBuffer phepToan) {
        String phepTinh = phepToan.toString();
        List<Integer> toanHang = new ArrayList<Integer>();
        List<Character> toanTu = new ArrayList<Character>();
        int lastIndexOfTT = 0;
        for(int i = 1; i < phepToan.length(); i++) {
            if (laToanTu(phepToan.charAt(i))) {
                String th = phepToan.substring(lastIndexOfTT, i);
                toanHang.add(Integer.parseInt(th));
                toanTu.add(phepToan.charAt(i));
                lastIndexOfTT = i + 1;
            }
        }
        toanHang.add(Integer.parseInt(phepToan.substring(lastIndexOfTT, phepToan.length())));

//      Loại bỏ toán tử * và /
        List<Integer> toanHang2 = new ArrayList<>();
        toanHang2.add(toanHang.get(0));
        for (int i = 0; i < toanTu.size(); i++) {
            char tt = toanTu.get(i);
            if (tt == '+' || tt == '-') {
                toanHang2.add(toanHang.get(i+1));
            }else if (toanTu.get(i) == '*') {
                toanHang2.set(toanHang2.size() - 1, toanHang2.get(toanHang2.size() - 1) * toanHang.get(i+1));
            } else if (toanTu.get(i) == '/') {
                toanHang2.set(toanHang2.size() - 1, toanHang2.get(toanHang2.size() - 1) / toanHang.get(i+1));
            }
        }

        int result = toanHang2.get(0);
        for (int i = 0; i < toanTu.size(); i++) {
            int index = 0;
            char tt = toanTu.get(i);
            if (tt == '+') {
                result += toanHang2.get(index + 1);
                index ++;
            } else if (tt == '-') {
                result -= toanHang2.get(index + 1);
            }
        }
        return result;
    }

    public boolean kiemTraDinhDang(StringBuffer phepToan) {
        if (phepToan.toString().equals("")) return false;
        if (laToanTu(phepToan.charAt(0))) {
            if (phepToan.charAt(0) == '-') {
                if (laToanTu(phepToan.charAt(phepToan.length()-1))) {
                    return false;
                } else
                    return true;
            }
        } else {
            if (laToanTu(phepToan.charAt(phepToan.length()-1))) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public char toanTu(int id) {
        if (id == R.id.mul_btn) return '*';
        else if (id == R.id.devide_btn) return '/';
        else if (id == R.id.add_btn) return '+';
        else return '-';
    }

    public boolean laToanTu(char t) {
        if (t == '+' || t == '-' || t == '/' || t == '*') {
            return true;
        } else return  false;
    }
}