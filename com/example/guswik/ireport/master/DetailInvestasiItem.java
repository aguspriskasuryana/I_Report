package com.example.guswik.ireport.master;

import com.example.guswik.ireport.database.DataHelper;

import java.util.ArrayList;
import java.util.Vector;

public class DetailInvestasiItem {

	//declare private data instead of public to ensure the privacy of data field of each class
	private String rba;
	private String volume;
	private String harga;
	private String total;

	public DetailInvestasiItem(String rba, String volume, String harga, String total) {
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

	public static ArrayList<DetailInvestasiItem> getDetailInvestasiItems(DataHelper dbHelper, long tahun) {
		ArrayList<DetailInvestasiItem> detailInvestasiItems = new ArrayList<DetailInvestasiItem>();

		Vector listsDetailPerhitunganInvestasi = new Vector();
		String whereclause = PerhitunganInvestasiDAO.FLD_TAHUN +" = " + tahun;
		listsDetailPerhitunganInvestasi = DetailPerhitunganInvestasiDAO.getList(dbHelper, 0,0,whereclause,"");

		if (listsDetailPerhitunganInvestasi.size()>0){
				for (int i = 0 ; i<listsDetailPerhitunganInvestasi.size(); i++) {
					DetailPerhitunganInvestasiEntity detailPerhitunganInvestasiEntity = (DetailPerhitunganInvestasiEntity) listsDetailPerhitunganInvestasi.get(i);
				//if (i == (lists.size() - 1)) {
					detailInvestasiItems.add(new DetailInvestasiItem(detailPerhitunganInvestasiEntity.getRBA(), String.valueOf(detailPerhitunganInvestasiEntity.getVolume()), String.valueOf(detailPerhitunganInvestasiEntity.getHarga()), String.valueOf(detailPerhitunganInvestasiEntity.getTotalharga()) ));
				//}
			}
		}

		return detailInvestasiItems;
	}
}
