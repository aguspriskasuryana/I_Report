package com.example.guswik.ireport.master;

import com.example.guswik.ireport.database.DataHelper;

import java.util.ArrayList;
import java.util.Vector;

public class DetailBiayaItem {

	//declare private data instead of public to ensure the privacy of data field of each class
	private String rba;
	private String volume;
	private String harga;
	private String total;

	public DetailBiayaItem(String rba, String volume, String harga, String total) {
		this.rba = rba;
		this.volume = volume;
		this.harga = harga;
		this.total = total;
	}

	public String getRBA(){
		return rba;
	}

	public String getVolume(){
		return volume;
	}

	public String getHarga(){
		return harga;
	}

	public String getTotal(){
		return total;
	}

	public static ArrayList<DetailBiayaItem> getDetailBiayaItems(DataHelper dbHelper, long tahun) {
		ArrayList<DetailBiayaItem> detailBiayaItems = new ArrayList<DetailBiayaItem>();

		Vector listsDetailPerhitunganBiaya = new Vector();
		String whereclause = DetailPerhitunganBiayaDAO.FLD_TAHUN +" = " + tahun;
		listsDetailPerhitunganBiaya = DetailPerhitunganBiayaDAO.getList(dbHelper, 0,0,whereclause,"");

		if (listsDetailPerhitunganBiaya.size()>0){
				for (int i = 0 ; i<listsDetailPerhitunganBiaya.size(); i++) {
					DetailPerhitunganBiayaEntity detailPerhitunganBiayaEntity = (DetailPerhitunganBiayaEntity) listsDetailPerhitunganBiaya.get(i);
				//if (i == (lists.size() - 1)) {
					detailBiayaItems.add(new DetailBiayaItem(detailPerhitunganBiayaEntity.getRBA(), String.valueOf(detailPerhitunganBiayaEntity.getVolume()), String.valueOf(detailPerhitunganBiayaEntity.getHarga()), String.valueOf(detailPerhitunganBiayaEntity.getTotalharga()) ));
				//}
			}
		}

		return detailBiayaItems;
	}
}
