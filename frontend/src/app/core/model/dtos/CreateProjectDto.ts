import {Project} from "../Project";


export class CreateProjectDto {
  public id?: number;
  public name?: string;
  public icon?: string;
  public description?: string;
  public git?: string;
  public color?: string;
  public abilities?: Array<number>;
  public technologies?: Array<number>;

  static fromProject(project: Project): CreateProjectDto{
    return new CreateProjectDto(
      project.name,
      project.icon,
      project.description,
      project.git,
      project.color,
      project.abilities.map(a => a.id),
      project.technologies.map(t => t.id)
    );
  }

  constructor(name: string,
              icon: string,
              description: string,
              git: string,
              color: string,
              abilities: Array<number>,
              technologies: Array<number>
    ) {
    this.name = name;
    this.icon = icon;
    this.description = description;
    this.git = git;
    this.color = color;
    this.abilities = abilities;
    this.technologies = technologies;
  }
}
