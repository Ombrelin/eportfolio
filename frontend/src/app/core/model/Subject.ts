import {Ability} from "./Ability";

export class Subject {
  constructor(
    public id?: number,
    public name?: string,
    public icon?: string,
    public image?: string,
    public abilities?: Array<Ability>) {
  }
}
