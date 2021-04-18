import {Technology} from "./Technology";

export class Ability {
  constructor(
    public id?: number,
    public name?: string,
    public color?: string,
    public image?: string,
    public technologies?: Array<Technology>) {
  }
}
