//do_t
#include "colors.inc"
#include "textures.inc"

background{color White}
                
camera
{
 location <0, 3, -8>
 look_at <0, 0, 0> 
}   

light_source { <-20, 40, -30> color White }

plane
{
 <0, 1, 0>, -6
 pigment{ checker color White, color Grey }
}
                        
#declare big_frame = difference
{
    
    box{<-2,-2, -0.25>, <2,2,2.25>} 
    box{<-1.75,-1.75, -0.3>, <1.75, 1.75, 2>}
    pigment{Yellow}
    
}                       

#declare lid = box 
{
    <-2+clock, -1.75, -0.5>, <2+clock, 1.75, -0.25>
    pigment { color Green }
}
        
object{big_frame rotate<45,0,45>} 

box {
    <-2,-2,-0.5>, <2,-1.75,-0.25> 
    pigment{Yellow}   
    rotate<45,0,45>
}

box {
    <-2,2,-0.5>, <2,1.75,-0.25> 
    pigment{Yellow}   
    rotate<45,0,45>
}
 
object{lid rotate<45,0,45>}
 