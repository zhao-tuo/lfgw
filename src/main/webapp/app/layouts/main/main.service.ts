import {EventEmitter, Injectable} from "@angular/core";
import { MenuData } from "./main.model";


@Injectable()
export class JhiMainService{

    switchMenu:EventEmitter<string>;
    constructor(){
        this.switchMenu = new EventEmitter<string>();
    }
}
