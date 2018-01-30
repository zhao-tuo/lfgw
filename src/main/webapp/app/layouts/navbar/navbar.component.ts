import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager, JhiLanguageService} from 'ng-jhipster';

import { ProfileService } from '../profiles/profile.service';
import { JhiLanguageHelper, Principal, LoginModalService, LoginService } from '../../shared';

import { VERSION } from '../../app.constants';
import {JhiMainService} from "../main/main.service";
import {MenuData} from "../../entities/menu-data/menu-data.model";
import {Observable} from "rxjs/Observable";


@Component({
    selector: 'jhi-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: [
        'navbar.scss'
    ]
})
export class NavbarComponent implements OnInit {
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;
    //rootMenus:MenuData[];

    @Output() toggle:EventEmitter<boolean>=new EventEmitter();
    //tree开关提示
    toggleDescTip:string = "点击关闭导航菜单";
    private navClose:boolean = true;

    constructor(
        private loginService: LoginService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        private router: Router,
        private jhiEventManager:JhiEventManager
    ) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
    }

    ngOnInit() {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });

        this.profileService.getProfileInfo().then((profileInfo) => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });
        this.toggle.emit(this.navClose);

        /*if(!this.mainService.menuData){
            this.mainService.findRootMenuData()
                .subscribe(
                    (data:MenuData[])=>{
                        this.mainService.menuData=data;
                        this.rootMenus=data;
                    }
                );
        }else{
            this.rootMenus=this.mainService.menuData;
        }*/
    }

    changeLanguage(languageKey: string) {
      this.languageService.changeLanguage(languageKey);
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        return this.isAuthenticated() ? this.principal.getImageUrl() : null;
    }

    //切换导航
    toggleNav(){
        this.navClose = !this.navClose;
        this.toggle.emit(this.navClose);
        if (this.navClose) {
            this.toggleDescTip = "点击展开导航菜单";
        } else {
            this.toggleDescTip = "点击关闭导航菜单";
        }
    }

    switchMenu(menuId:string){
        this.jhiEventManager.broadcast({name:"switchMenu",data:menuId});
        //this.mainService.switchMenu.emit(menuId);
        if(this.navClose){
            this.navClose = false;
            this.toggle.emit(this.navClose);
        }
    }
}
