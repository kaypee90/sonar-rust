fn main() {
    println!("Checking issues");
    absurd_extreme_comparison();
    println!("Done");
    as_conversions();
}

fn absurd_extreme_comparison(){
    let vec: Vec<isize> = Vec::new();
    if vec.len() <= 0 {}
    if 100 > std::i32::MAX {}
}

fn as_conversions(){
    let i = 0u32 as u64;

    let _j = &i as *const u64 as *mut u64;
}