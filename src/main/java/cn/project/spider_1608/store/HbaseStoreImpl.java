package cn.project.spider_1608.store;

import java.io.IOException;
import java.util.Map;

import cn.project.spider_1608.domain.Page;
import cn.project.spider_1608.utils.HbaseUtils;
/**
 * create 'spider' ,'goodsinfo','spec'
 * goodsinfo这个列簇的版本号需要设置，建议设置为30个
 * alter 'spider',{NAME=>'goodsinfo',VERSIONS=>30}
 * rowkey的设计(预分区0-9)
 * 使用商品的编号，但是要倒置(避免热点问题)，还要再后面加上一个网站的唯一标识，因为多个电商网站商品编号可能重复
 * 例如：原始商品编号：1861098
 * 最终的编号：8901681_jd
 * 
 * @author asus
 *
 */
public class HbaseStoreImpl implements Storeable {

	HbaseUtils hbaseUtils = new HbaseUtils();
	@Override
	public void store(Page page) {
		String goodsid = page.getGoodsid();
		Map<String, String> map = page.getMap();
		try {
			hbaseUtils.put(HbaseUtils.TABLE_NAME, goodsid, HbaseUtils.COLUMNFAMILY_1, HbaseUtils.COLUMNFAMILY_1_DATA_URL, page.getUrl());
			hbaseUtils.put(HbaseUtils.TABLE_NAME, goodsid, HbaseUtils.COLUMNFAMILY_1, HbaseUtils.COLUMNFAMILY_1_PIC_URL, map.get("picpath"));
			hbaseUtils.put(HbaseUtils.TABLE_NAME, goodsid, HbaseUtils.COLUMNFAMILY_1, HbaseUtils.COLUMNFAMILY_1_PRICE, map.get("price"));
			hbaseUtils.put(HbaseUtils.TABLE_NAME, goodsid, HbaseUtils.COLUMNFAMILY_1, HbaseUtils.COLUMNFAMILY_1_TITLE, map.get("title"));
			hbaseUtils.put(HbaseUtils.TABLE_NAME, goodsid, HbaseUtils.COLUMNFAMILY_2, HbaseUtils.COLUMNFAMILY_2_PARAM, map.get("spec"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
