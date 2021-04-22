import {Ability} from "./Ability";
import {Technology} from "./Technology";

export class Project {
  constructor(
    public id?: number,
    public name?: string,
    public icon?: string,
    public description?: string,
    public git?: string,
    public color?: string,
    public abilities?: Array<Ability>,
    public technologies?: Array<Technology>
  ) {
    if(!abilities){
      this.abilities = new Array<Ability>();
    }
    if(!technologies){
      this.technologies = new Array<Technology>();
    }
  }
}
