package org.eredlab.g4.arm.service.impl;

import java.util.List;

import org.eredlab.g4.arm.service.PartService;
import org.eredlab.g4.arm.util.idgenerator.IDHelper;
import org.eredlab.g4.bmf.base.BaseServiceImpl;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;

/**
 * UI组件授权服务实现
 * 
 * @author XiongChun
 * @since 2011-06-25
 */
public class PartServiceImpl extends BaseServiceImpl implements PartService {
	
	/**
	 * 保存托管UI组件脏数据
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto saveDirtyDatas(Dto pDto){
		List list = pDto.getDefaultAList();
		for (int i = 0; i < list.size(); i++) {
			Dto dto = (BaseDto)list.get(i);
			if (dto.getAsString("dirtytype").equalsIgnoreCase("1")) {
				dto.put("partid", IDHelper.getPartID());
				g4Dao.insert("Part.savePartItem", dto);
			}else {
				g4Dao.update("Part.updatePartItem", dto);
			}
		}
		return null;
	}
	
	/**
	 * 删除数据
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto deleteItem(Dto pDto){
		g4Dao.delete("Part.deletePartItem", pDto);
		return null;
	}
}
