import {EventEmitter, Injectable} from "@angular/core";
import { MenuData } from '../../entities/menu-data';
import {Observable} from "rxjs/Observable";
import {Http,Response} from "@angular/http";
import {SERVER_API_URL} from "../../app.constants";
import {MenuDataService} from "../../entities/menu-data/menu-data.service";
import {Input} from "@angular/compiler/src/core";


@Injectable()
export class JhiMainService{
    private resourceRootUrl = SERVER_API_URL + 'api/_root/menu-data';

    //switchMenu:EventEmitter<string>;

    private _menuData:MenuData[];

    constructor(private http:Http,private menuDataService:MenuDataService){
        //this.switchMenu = new EventEmitter<string>();
    }

    findRootMenuData() :Observable<MenuData[]>{
        return this.http.get(this.resourceRootUrl).map((res: Response) => {
            console.log(JSON.stringify(res.json()));
            return res.json();
        });
    }

    get menuData(): MenuData[] {
        return this._menuData;
    }

    set menuData(value: MenuData[]) {
        this._menuData = value;
    }

    refreshMenuData() {
        this.findRootMenuData().subscribe((data)=>this._menuData=data);
    }
}
