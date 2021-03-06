
package vn.t3h.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.t3h.data.UploadsFiles;
import vn.t3h.services.CategoryService;

@Table(name = "product")
@Entity
@NoArgsConstructor @AllArgsConstructor
@Setter @Getter
public class Product implements UploadsFiles, Serializable {

	private static final long serialVersionUID = 4641853311314844969L;
	public static Integer STATUS_ACTICE = 1;
	public static Integer STATUS_DEACTICE = 0;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id", unique = true, nullable = false)
    private Integer id;
	
	@Column(name="category_id")
	private Integer categoryId;
	
	@NotEmpty(message="Tên sản phẩm không được để trống")
	@Column(name="name")
	private String name;
	
	@Column(name="title")
	private String title;
	
	@Column(name="`desc`")
	private String desc;
	
	@Column(name="content")
	private String content;
	
	@NotNull(message="Giá tham khảo không được để trống")
	@Column(name="`price_ref`")
	private Integer priceRef;
	
	@NotNull(message="Giá bán không được để trống")
	@Column(name="`price_sale`")
	private Integer priceSale;
	
	@Column(name="`status`")
	private Integer status;
	
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name="create_time")
	private Date createTime;
	
	public String statusLable() {
		return mapStatus().get(status);
	}
	
	public String lableCategory() {
		var listCategory = CategoryService.getIntance().categoryWithIndent();
		try {
			Category category = listCategory.stream().filter(item -> {
				return categoryId.equals(item.getId());
			}).findAny().get();
			return Optional.ofNullable(category.getName()).orElse("--");
		} catch (Exception e) {
			return "--";
		}
	}
	
	public Map<Integer, String> mapStatus() {
		var maps = new HashMap<Integer, String>();
		maps.put(STATUS_ACTICE, "Kích hoạt");
		maps.put(STATUS_DEACTICE, "Ngưng");
		return maps;
	}
	
	@Transient
	// Khai báo đây không phải là cột trong db, để spring không mapping với model.
	public List<MultipartFile> multipartFile;

	public static String UPLOAD_PATH =  "/uploads/product/";
	
	@Override
	public String folderUpload() {
  		return UPLOAD_PATH;
	}

	@Override
	public List<String> filePass() {
		return Arrays.asList("png", "jpg");
	}
}
