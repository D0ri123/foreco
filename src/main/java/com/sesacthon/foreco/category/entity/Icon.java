package com.sesacthon.foreco.category.entity;

public enum Icon {

  //재활용 가능 종이
  BOX("box.png"),
  NEWSPAPER("newspaper.png"),
  BOOK("book.png"),
  PAPER_BAG("paperBag.png"),
  AD_PAPER("adPaper.png"),
  WORK_PAPER("workPaper.png"),
  WALL_PAPER("wallPaper.png"),
  BUJIGPO("bujigpo.png"),
  RECEIPT("receipt.png"),

  //플라스틱
  PLASTIC("plastic.png"),
  SPICE_JAR("spiceJar.png"),
  STYROFOAM("styrofoam.png"),
  AIRCAP("airCap.png"),
  PLASTIC_WRAP("plasticWrap.png"),
  RAINCOAT("raincoat.png"),
  PAPER_PACK("paperPack.png"),
  MOP("mop.png"),

  //비닐
  VINYL("vinyl.png"),
  VINYL_BAG("vinylBag.png"),
  VINYL_AD_PAPER("adPaper.png"),
  VINYL_WORK_PAPER("workPaper.png"),

  //대형폐기물
  SOFA("sofa.png"),
  BICYCLE("bicycle.png"),
  BED("bed.png"),

  //폐건전지, 폐형광등, 헌옷 - (특수)
  BATTERY("battery.png"),
  LAMP("lamp.png"),
  OLD_CLOTHES("oldClothes.png"),

  //일반쓰레기
  UMBRELLA("umbrella.png"),
  TOOTH_BRUSH("toothBrush.png"),
  WASTE_TISSUE("wasteTissue.png"),
  BANNER("banner.png"),
  DELIVERY_DISH("deliveryDish.png"),
  TENT("tent.png"),
  EGG("egg.png");

  private final String iconFile;

  Icon(String iconFile) {
    this.iconFile = iconFile;
  }

  public String getIconFile() {
    return this.iconFile;
  }

}
