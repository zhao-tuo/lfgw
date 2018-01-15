import { Component, OnInit } from '@angular/core';
import {MainData, MenuData} from '../main/main.model';
import {JhiMainService} from "../main/main.service";

@Component({
    selector: 'jhi-tree',
    templateUrl: './tree.component.html',
    styleUrls: [
        'tree.scss'
    ]
})
export class TreeComponent implements OnInit {

    allData: MainData = {
        userData: {
            userName: "赵佗",
            userAvatar: "../../../content/img/user-header.png",
            mobilePhone: "1860010***4",
            email: "657462305@qq.com",
            positions: "Java工程师、打杂工程师",
        },
        menuData: [
            {
                "id": "1",
                "parentId": "0",
                "name": "业务数据",
                "keyWord": "ywsj",
                "icon": "fa-cube",
                "isExpend":true,
                "children": [
                    {
                        "id": "11",
                        "parentId": "1",
                        "name": "项目信息",
                        "keyWord": "xmxx",
                        "icon": "fa-dashboard",
                        "url": "project-info"
                    },
                    {
                        "id": "12",
                        "parentId": "1",
                        "name": "商铺信息",
                        "keyWord": "spxx",
                        "icon": "fa-sticky-note",
                        "url": "shop-info"
                    }
                ]
            },
            {
                "id": "2",
                "parentId": "0",
                "name": "系统管理",
                "keyWord": "xtgl",
                "icon": "fa-cube",
                "isExpend":true,
                "children": [
                    {
                        "id": "21",
                        "parentId": "2",
                        "name": "网关",
                        "keyWord": "xmxx",
                        "icon": "fa-dashboard",
                        "url": "gateway"
                    },
                    {
                        "id": "22",
                        "parentId": "2",
                        "name": "用户管理",
                        "keyWord": "spxx",
                        "icon": "fa-sticky-note",
                        "url": "user-management"
                    }
                ]
            }
        ]
    };

    //用户数据
    mainData: MainData = new MainData();
    showdata:string;
    constructor(private mainService:JhiMainService) {
    }

    ngOnInit() {
        this.mainData.userData = this.allData.userData;
        this.allData.menuData.forEach((val:MenuData,idx,arr)=>{
            if(val.id==='2'){
                this.mainData.menuData = [val];
            }
        });
        //this.mainData.userData = Object.assign(this.mainData.userData,this.allData.userData);
        this.mainService.switchMenu.subscribe((value:string)=>{
            this.mainData.userData = this.allData.userData;
            this.allData.menuData.forEach((val:MenuData,idx,arr)=>{
                if(val.id===value){
                    this.mainData.menuData = [val];
                }
            });
        });

        this.showdata = JSON.stringify(this.mainData);

    }

}
