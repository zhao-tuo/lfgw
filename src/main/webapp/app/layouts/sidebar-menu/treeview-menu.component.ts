import { Component, OnInit, Input } from '@angular/core';
import {  Router } from '@angular/router';
import { MenuData } from '../../entities/menu-data';
/**
 * 菜单树组件
 */
@Component({
  selector: 'c-treeview-menu',
  templateUrl:'./treeview-menu.component.html',
  styleUrls:['./treeview-menu.component.scss']
})

export class TreeviewMenuComponent {

  @Input() data:MenuData;


  constructor(private router: Router) {}


  /**
   * 是否有子节点
   * @param item
   */
  isLeaf(item: MenuData) {
    return !item.children || !item.children.length;
  }

  /**
   * 点击
   * @param item
   */
  itemClicked(item: MenuData) {
    if (!this.isLeaf(item)) {
      item.expended = !item.expended;
    } else {
       this.router.navigate([item.url]);
    }
  }





}
