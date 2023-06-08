package com.example.guswik.ireport.master;

import com.example.guswik.ireport.database.DataHelper;

import java.util.ArrayList;
import java.util.Vector;

public class DetailPendapatanItem {

	//declare private data instead of public to ensure the privacy of data field of each class
	private String rba;
	private String volume;
	private String harga;
	private String total;

	public DetailPendapatanItem(String rba, String volume, String harga, String total) {
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

	public static ArrayList<DetailPendapatanItem> getDetailPendapatanItems(DataHelper dbHelper, long tahun) {
		ArrayList<DetailPendapatanItem> detailPendapatanItems = new ArrayList<DetailPendapatanItem>();

		Vector listsDetailPerhitunganPendapatan = new Vector();
		String whereclause = DetailPerhitunganPendapatanDAO.FLD_TAHUN +" = " + tahun;
		listsDetailPerhitunganPendapatan = DetailPerhitunganPendapatanDAO.getList(dbHelper, 0,0,whereclause,"");

		if (listsDetailPerhitunganPendapatan.size()>0){
				for (int i = 0 ; i<listsDetailPerhitunganPendapatan.size(); i++) {
					DetailPerhitunganPendapatanEntity detailPerhitunganPendapatanEntity = (DetailPerhitunganPendapatanEntity) listsDetailPerhitunganPendapatan.get(i);
				//if (i == (lists.size() - 1)) {
					detailPendapatanItems.add(new DetailPendapatanItem(detailPerhitunganPendapatanEntity.getRBA(), String.valueOf(detailPerhitunganPendapatanEntity.getVolume()), String.valueOf(detailPerhitunganPendapatanEntity.getHarga()), String.valueOf(detailPerhitunganPendapatanEntity.getTotalharga()) ));
				//}
			}
		}

		return detailPendapatanItems;
	}
}
