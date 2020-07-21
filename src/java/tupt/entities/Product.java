/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tupt.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sherl
 */
@Entity
@Table(name = "Product")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
    , @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id = :id")
    , @NamedQuery(name = "Product.findByName", query = "SELECT p FROM Product p WHERE p.name = :name")
    , @NamedQuery(name = "Product.findByImageUrl", query = "SELECT p FROM Product p WHERE p.imageUrl = :imageUrl")
    , @NamedQuery(name = "Product.findBySize", query = "SELECT p FROM Product p WHERE p.size = :size")
    , @NamedQuery(name = "Product.findByColor", query = "SELECT p FROM Product p WHERE p.color = :color")
    , @NamedQuery(name = "Product.findByPrice", query = "SELECT p FROM Product p WHERE p.price = :price")
    , @NamedQuery(name = "Product.findByUnit", query = "SELECT p FROM Product p WHERE p.unit = :unit")
    , @NamedQuery(name = "Product.findCement", query = "SELECT p FROM Product p WHERE p.name LIKE 'xi măng%' AND p.unit = 'tấn'")
    , @NamedQuery(name = "Product.findRock", query = "SELECT p FROM Product p WHERE p.name LIKE 'đá 1x2%'")
    , @NamedQuery(name = "Product.findSand", query = "SELECT p FROM Product p WHERE p.name LIKE 'cát%vàng'")
    , @NamedQuery(name = "Product.findSteel", query = "SELECT p FROM Product p WHERE p.name LIKE 'thép%' AND p.unit = 'kg'")
    , @NamedQuery(name = "Product.findBrick", query = "SELECT p FROM Product p WHERE p.name LIKE 'gạch%6 lỗ%'")
    , @NamedQuery(name = "Product.findTile", query = "SELECT p FROM Product p WHERE p.name LIKE 'ngói lợp%' OR p.name LIKE 'ngói màu%'")
    , @NamedQuery(name = "Product.findByLikeName", query = "SELECT p FROM Product p WHERE p.name LIKE :name ORDER BY p.occurrence DESC")
    , @NamedQuery(name = "Product.findByLikeNamePagination", query = "SELECT p FROM Product p WHERE p.name LIKE :name ORDER BY p.name DESC")
})
@NamedNativeQueries({
    @NamedNativeQuery(name = "Product.findProductByTag", query = "SELECT TOP 10 a.ID FROM " +
                                                        "(SELECT p.ID, COUNT(*) as Num " +
                                                        "FROM Product p " +
                                                        "JOIN TagProduct tp ON p.ID = tp.productID " +
                                                        "JOIN Tag t ON t.ID = tp.tagID " +
                                                        "WHERE t.Name = ? or t.Name = ? or t.Name = ? or t.Name = ? or t.Name = ? " +
                                                        "GROUP BY p.ID) a " +
                                                        "WHERE a.Num = 5"),
    @NamedNativeQuery(name="findFavoriteProduct", query = "SELECT CAST ((SELECT p.* FROM Product p "
                                                        + "JOIN Favorite f on f.ProductID = p.ID "
                                                        + "WHERE AccountID = ? "
                                                        + "FOR XML PATH('product'), Root('favorites')) "
                                                        + "AS NVARCHAR(MAX)) "
                                                        + "AS XMLDATA"),
    @NamedNativeQuery(name="findTrendingProduct", query = "SELECT CAST ((SELECT TOP 3 p.* FROM Product p "
                                                        + "WHERE p.Occurrence > 0 "
                                                        + "ORDER BY p.Occurrence DESC "
                                                        + "FOR XML PATH('product'), Root('trending')) "
                                                        + "AS NVARCHAR(MAX)) "
                                                        + "AS XMLDATA"),
})
public class Product implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productID")
    private Collection<TagProduct> tagProductCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 50)
    @Column(name = "Name")
    private String name;
    @Size(max = 200)
    @Column(name = "Url")
    private String url;
    @Size(max = 200)
    @Column(name = "ImageUrl")
    private String imageUrl;
    @Size(max = 10)
    @Column(name = "Size")
    private String size;
    @Size(max = 100)
    @Column(name = "Color")
    private String color;
    @Column(name = "Price")
    private Integer price;
    @Size(max = 10)
    @Column(name = "Unit")
    private String unit;
    @Column(name = "Occurrence")
    private Integer occurrence;
    @JoinColumn(name = "Category", referencedColumnName = "ID")
    @ManyToOne
    private Category category;
    @JoinColumn(name = "Supplier", referencedColumnName = "ID")
    @ManyToOne
    private Supplier supplier;

    public Product() {
    }

    public Product(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(Integer occurrence) {
        this.occurrence = occurrence;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tupt.entities.Product[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<TagProduct> getTagProductCollection() {
        return tagProductCollection;
    }

    public void setTagProductCollection(Collection<TagProduct> tagProductCollection) {
        this.tagProductCollection = tagProductCollection;
    }

}
