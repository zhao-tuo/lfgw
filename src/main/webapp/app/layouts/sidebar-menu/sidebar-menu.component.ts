import { Component, OnInit } from '@angular/core';
import { MenuData } from '../../entities/menu-data';
import { JhiMainService } from "../main/main.service";
import { Observable } from "rxjs/Observable";
import { Router } from "@angular/router";
import {JhiEventManager} from "ng-jhipster";

@Component({
    selector: 'jhi-sidebar',
    templateUrl: './sidebar-menu.component.html',
    styleUrls: [
        'sidebar-menu.component.scss'
    ]
})
export class SidebarMenuComponent implements OnInit {

    //用户数据
    menuData: MenuData[]=[
        {
            "id": 0,
            "parentId": null,
            "name": "首页",
            "keyWord": "s-page",
            "icon": "fa-cube",
            "expended":true,
            "url":"",
            "children": []
        }
    ];

    constructor(private mainService:JhiMainService,private router:Router,private jhiEventManager:JhiEventManager) {
    }

    ngOnInit() {
        this.jhiEventManager.subscribe("switchMenu",($event:any)=> {
            if (!this.mainService.menuData) {
                this.mainService.findRootMenuData()
                    .subscribe(
                        (data: MenuData[]) => {
                            this.mainService.menuData = data;
                            Observable.of(...data)
                                .first((menuData) => menuData.keyWord == $event.data)
                                .subscribe((menuData) => this.menuData = menuData.children);
                        }
                    );
            } else {
                Observable.of(...this.mainService.menuData)
                    .first((data: MenuData) => data.keyWord == $event.data)
                    .subscribe((data: MenuData) => this.menuData = data.children);
            }
        });
        /*this.mainService.switchMenu.subscribe((value:string)=>{
            if(!this.mainService.menuData){
                this.mainService.findRootMenuData()
                    .subscribe(
                        (data:MenuData[])=>{
                            this.mainService.menuData=data;
                            Observable.of(...data)
                                .first((menuData)=>menuData.keyWord==value)
                                .subscribe((menuData)=>this.menuData = menuData.children);
                        }
                        );
            }else{
                Observable.of(...this.mainService.menuData)
                    .first((data:MenuData)=>data.keyWord==value)
                    .subscribe((data:MenuData)=>this.menuData = data.children);
            }
        },()=>null,()=>console.log(JSON.stringify(this.menuData)))*/
    }


    /**
     * 是否有子节点
     * @param item
     */
    isLeaf(item:MenuData){
        return !item.children || !item.children.length;
    }

    /**
     * 点击s
     * @param item
     */
    itemClicked(item:MenuData){
        if(!this.isLeaf(item)) {
            for(let obj of this.menuData){
                if(obj.id!=item.id){
                    obj.expended=false;
                }
            }
            item.expended = !item.expended;
        }else {
            this.router.navigate([item.url]);
        }
    }
}
