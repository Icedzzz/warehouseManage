package com.cms.sys.common;

import java.util.ArrayList;
import java.util.List;

public class TreeNodeBuilder {

    /**
     * 把没用层级关系的集合变成有层级关系的
     * @param treeNodes
     * @param topPid
     * @return
     */
  public static List<TreeNode> build(List<TreeNode> treeNodes,Integer topPid){
      ArrayList<TreeNode> nodes = new ArrayList<>();
      for (TreeNode treeNode1 : treeNodes) {
       if (treeNode1.getPid()==topPid){
           nodes.add(treeNode1);
       }
          for (TreeNode treeNode2 : treeNodes) {
              if (treeNode1.getId()==treeNode2.getPid()){
                  treeNode1.getChildren().add(treeNode2);
              }
          }


      }
      return nodes;
  }
}
